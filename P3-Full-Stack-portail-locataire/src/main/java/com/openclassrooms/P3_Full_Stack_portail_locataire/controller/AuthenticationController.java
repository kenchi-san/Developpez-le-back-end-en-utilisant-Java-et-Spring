package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.LoginUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.RegisterUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.response.LoginResponse;
import com.openclassrooms.P3_Full_Stack_portail_locataire.response.RegisterResponse;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.AuthenticationService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            // Appel du service pour enregistrer l'utilisateur
            User registeredUser = authenticationService.signup(registerUserDto);

            // Création de la réponse
            RegisterResponse registerResponse = new RegisterResponse(
                    registeredUser.getId(),
                    registeredUser.getName(),
                    registeredUser.getEmail(),
                    "Inscription réussie"
            );

            // Retour de la réponse HTTP
            return ResponseEntity.ok(registerResponse);

        } catch (IllegalArgumentException e) {
            // Cas où l'utilisateur existe déjà ou mauvais format des données
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RegisterResponse(null, null,null, "Échec de l'inscription : " + e.getMessage()));

        } catch (Exception e) {
            // Gestion des erreurs générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RegisterResponse(null, null,null, "Erreur interne du serveur"));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            // Authentification de l'utilisateur
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            // Génération du token JWT
            String jwtToken = jwtService.generateToken(authenticatedUser);

            // Construction de la réponse
            LoginResponse loginResponse = new LoginResponse(
                    jwtToken,
                    jwtService.getExpirationTime(),
                    "Authentification réussie"
            );

            // Retour de la réponse HTTP
            return ResponseEntity.ok(loginResponse);

        } catch (IllegalArgumentException e) {
            // Mauvais identifiants
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, 0, "Identifiants incorrects"));
        } catch (Exception e) {
            // Autres erreurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, 0, "Erreur interne du serveur"));
        }
    }
}
