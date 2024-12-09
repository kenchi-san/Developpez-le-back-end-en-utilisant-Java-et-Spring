package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.LoginUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.MeUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.RegisterUserDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.response.LoginResponse;
import com.openclassrooms.P3_Full_Stack_portail_locataire.response.RegisterResponse;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.AuthenticationService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.JwtService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
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
                    .body(new RegisterResponse(null, null, null, "Échec de l'inscription : " + e.getMessage()));

        } catch (Exception e) {
            // Gestion des erreurs générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RegisterResponse(null, null, null, "Erreur interne du serveur"));
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


    @GetMapping("/me")
    public ResponseEntity<MeUserDto> authenticatedUser() {
        // Obtenez l'authentification actuelle
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Vérifiez si l'utilisateur est authentifié
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }

        // Récupérez l'email ou le nom d'utilisateur à partir du principal
        String email = authentication.getName(); // Généralement l'email ou username

        // Chargez l'utilisateur depuis la base de données
        User currentUser = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Mappez l'entité User vers le DTO
        MeUserDto meUserDto = new MeUserDto(
                currentUser.getId(),
                currentUser.getEmail(),
                currentUser.getName(),
                currentUser.getCreatedAt(),
                currentUser.getUpdatedAt()
        );

        // Retournez le DTO
        return ResponseEntity.ok(meUserDto);
    }
}
