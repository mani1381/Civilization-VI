module com.example.civilization {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.civilization.Model.GlobalChats to com.google.gson;
    opens com.example.civilization.FXMLcontrollers to javafx.fxml;
    opens com.example.civilization.Model to com.google.gson;
    opens com.example.civilization.Model.Technologies to com.google.gson;
    opens com.example.civilization.Model.Units to com.google.gson;
    opens com.example.civilization.Model.Terrains to com.google.gson;
    opens com.example.civilization.Model.TerrainFeatures to com.google.gson;
    opens com.example.civilization.Model.Improvements to com.google.gson;
    opens com.example.civilization.Model.Resources to com.google.gson;
    opens com.example.civilization.Model.City to com.google.gson;
    opens com.example.civilization.Model.Buildings to com.google.gson;
    opens com.example.civilization.Controllers to com.google.gson;
    exports com.example.civilization to javafx.graphics;
    exports com.example.civilization.FXMLcontrollers to javafx.fxml;
    exports com.example.civilization.View to javafx.fxml;
    opens com.example.civilization.View to javafx.fxml;
}