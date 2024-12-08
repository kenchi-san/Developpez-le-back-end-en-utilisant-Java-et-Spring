//package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;
//
//import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.MeUserDto;
//import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
//import com.openclassrooms.P3_Full_Stack_portail_locataire.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequestMapping("/users")
//@RestController
//public class UserController {
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<MeUserDto> authenticatedUser() {
//        // Obtenez l'authentification actuelle
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Vérifiez si l'utilisateur est authentifié
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
//        }
//
//        // Récupérez l'email ou le nom d'utilisateur à partir du principal
//        String email = authentication.getName(); // Généralement l'email ou username
//
//        // Chargez l'utilisateur depuis la base de données
//        User currentUser = userService.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
//
//        // Mappez l'entité User vers le DTO
//        MeUserDto meUserDto = new MeUserDto(
//                currentUser.getId(),
//                currentUser.getEmail(),
//                currentUser.getName(),
//                currentUser.getCreatedAt()
//        );
//
//        // Retournez le DTO
//        return ResponseEntity.ok(meUserDto);
//    }
//}
