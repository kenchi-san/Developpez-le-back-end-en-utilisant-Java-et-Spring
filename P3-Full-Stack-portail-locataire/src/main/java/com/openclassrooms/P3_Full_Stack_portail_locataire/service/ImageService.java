package com.openclassrooms.P3_Full_Stack_portail_locataire.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    @Value("${upload.dir}")  // Définir le répertoire via application.properties ou application.yml
    private String uploadDir;
    private final String baseUrl = "/imgs/";
    // Méthode pour enregistrer l'image dans le répertoire `static/imgs`
    public String savePicture(MultipartFile picture) {
        try {
            // Déterminer le répertoire de stockage
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();  // Créez le répertoire si nécessaire
            }

            // Récupérer le nom du fichier
            String fileName = picture.getOriginalFilename();
            File fileToSave = new File(uploadDirFile, fileName);

            // Sauvegarder l'image dans le répertoire `static/imgs`
            picture.transferTo(fileToSave);

            // Retourner l'URL de l'image, accessible sous /imgs/nom_de_fichier
            return "api/imgs/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'image.", e);
        }
    }

    // Méthode pour supprimer une image, si nécessaire lors de l'édition
    public void deletePicture(String imageUrl) {
        File fileToDelete = new File(uploadDir, imageUrl.replace("/imgs/", ""));
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }
    public String getImageUrl(String pictureName) {
        // Retourner l'URL complète de l'image
        return baseUrl + pictureName;
    }
}
