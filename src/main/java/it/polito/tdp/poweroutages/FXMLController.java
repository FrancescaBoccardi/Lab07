/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	int n1;
    	int n2;
    	
    	try {
    		n1 = Integer.parseInt(txtYears.getText());
        	n2 = Integer.parseInt(txtHours.getText());
    	} catch(NumberFormatException ne) {
    		txtResult.setText("Ore e anni devono essere espressi in valori esclusivamente numerici!");
    		return;
    	}
    			
		List<PowerOutage> pws = model.trovaSequenzaOttima(cmbNerc.getValue(), n1, n2);
		
		StringBuilder sb = new StringBuilder(); 
		
		sb.append("Tot people affected: "+model.getMassimo()+"\n");
		sb.append("Tot hours of outage: "+model.calcolaOreTotali(pws)+"\n");
    	
    	for(PowerOutage pw : pws) {
    		sb.append(String.format("%-6d ", pw.getDataFine().getYear())); 
    		sb.append(String.format("%-20s ", pw.getDataInizio())); 
    		sb.append(String.format("%-20s ", pw.getDataFine()));
    		sb.append(String.format("%-4d", pw.getDurata()/3600));
    		sb.append(String.format("%-10d\n", pw.getNumCustomer()));
        }
    		
    		txtResult.appendText(sb.toString());
    	}
    	
    	


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	cmbNerc.getItems().addAll(model.getNercList());
    }
}
