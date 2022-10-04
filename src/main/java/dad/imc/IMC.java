package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application{
	
	//view
	private TextField pesoText;
	private TextField alturaText;
	private Label imcLabel;
	private Label resultadolLabel;
	
	//model
	private DoubleProperty altura = new SimpleDoubleProperty();
	private DoubleProperty peso = new SimpleDoubleProperty();
	private DoubleProperty imc = new SimpleDoubleProperty();
	private StringProperty resultado = new SimpleStringProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		pesoText = new TextField();
		pesoText.setPrefWidth(80);
		
		alturaText = new TextField();	
		alturaText.setPrefWidth(80);
		
		imcLabel = new Label();
		
		resultadolLabel = new Label();
		resultadolLabel.setText("Bajo peso | Normal | Sobrepeso | Obeso");
		
		HBox pesoBox = new HBox(5,new Label("Peso:"),pesoText,new Label("kg"));
		pesoBox.setAlignment(Pos.CENTER);
		
		HBox alturaBox = new HBox(5,new Label("Altura:"),alturaText,new Label("cm"));
		alturaBox.setAlignment(Pos.CENTER);

		HBox imcBox = new HBox(5,new Label("IMC:"),imcLabel);
		imcBox.setAlignment(Pos.CENTER);

		VBox rootPanel = new VBox();
		rootPanel.setSpacing(5);
		rootPanel.setAlignment(Pos.CENTER);
		rootPanel.setFillWidth(false);
		rootPanel.getChildren().addAll(pesoBox,alturaBox,imcBox,resultadolLabel);
		
		Scene scene = new Scene(rootPanel, 320, 200);
		
		primaryStage.setTitle("IMC.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//bindings
		pesoText.textProperty().bindBidirectional(peso, new NumberStringConverter());
		alturaText.textProperty().bindBidirectional(altura, new NumberStringConverter());
		imcLabel.textProperty().bind(imc.asString("%.2f"));
		resultadolLabel.textProperty().bind(resultado);
		
		DoubleExpression alturaMetros = altura.divide(100);
		
		imc.bind(peso.divide(alturaMetros.multiply(alturaMetros)));
		
		imc.addListener((o, ov, nv) -> {
			String imcResultado="";
			double newValue = nv.doubleValue();
			if(newValue < 18.5) {
				imcResultado="Bajo peso";
			} else if(newValue >= 18.5 && newValue < 25) {
				imcResultado="Normal";
			} else if(newValue >= 25 && newValue < 30) {
				imcResultado="Sobrepeso";
			} else if(newValue >= 30){
				imcResultado="Obeso";
			}
			resultado.set(imcResultado);
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
