package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.LoginUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.RegisterUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.response.LoginResponse;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.AuthenticationService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {

            // Appelle le service d'authentification pour inscrire l'utilisateur
            User registeredUser = authenticationService.signup(registerUserDto);

            // Retourne la réponse HTTP 200 avec l'utilisateur inscrit
            return ResponseEntity.ok(registeredUser);

        } catch (IllegalArgumentException e) {
            // En cas d'email déjà utilisé ou autre erreur d'argument
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already in use: " + registerUserDto.getEmail());
        } catch (Exception e) {
            // En cas d'erreur interne du serveur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal error occurred during registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {

        try {
            // Authentifie l'utilisateur via le service authentication
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            // Génère un token JWT pour l'utilisateur authentifié
            String jwtToken = jwtService.generateToken(authenticatedUser);

            // Prépare la réponse contenant le token et sa durée de validité
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            // Retourne une réponse HTTP 200 avec le corps de la réponse
            return ResponseEntity.ok(loginResponse);
        } catch (IllegalArgumentException e) {
            // Mauvais identifiants ou utilisateur non trouvé
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse());
        } catch (Exception e) {
            // Autres erreurs (par exemple des erreurs internes)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse());
        }
    }
}