// module-info.java
module com.example.graphicaluserinterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.graphicaluserinterface to javafx.fxml;
    exports com.example.graphicaluserinterface;
}
