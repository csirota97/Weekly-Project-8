package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import res.Contact;

public class ListSelectorController extends Application implements Serializable {

	private static Stage mainStage;
	ObservableList<String> files = FXCollections.observableArrayList();
	private static ListView<String> lv;
	private String storeDir = "src"+File.separator+"albums"+File.separator;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		
		primaryStage.setTitle("ListServer");
		primaryStage.setResizable(false);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ListSelectorView.fxml"));
			Scene scene = new Scene(root);
			File f = new File(storeDir);

			File[] fileList = new File(storeDir).listFiles();
			for (File f1: fileList) {
				System.out.println(f1.getName().substring(0, f1.getName().length()-5));
				System.out.println(files.size());
				files.add(f1.getName().substring(0, f1.getName().length()-5));
			}

			System.out.println(files.size());

			lv = (ListView) scene.lookup("#listView");
			lv.setItems(files);
			
			primaryStage.setOnCloseRequest(e -> {
		        Platform.exit();
		        System.exit(0);
		    });
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mainStage = primaryStage;
		
	}
	
	@FXML
	private void select(ActionEvent e) {
		int i = lv.getSelectionModel().getSelectedIndex();
		if (i!=-1) {
			ListController.name = (String) lv.getSelectionModel().getSelectedItem();
			ListController.contacts.clear();
			try {
				(new ListController()).start(mainStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	@FXML
	private void add(ActionEvent e) {
		String name = "";
		while(name.equals("") || name == null){
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("New List");
			dialog.setHeaderText("Please Name The New List");
			dialog.showAndWait();
			name = dialog.getEditor().getText();
		}
		try {
			new ObjectOutputStream(new FileOutputStream(storeDir + name + ".list"));
			files.clear();
			File[] fileList = new File(storeDir).listFiles();
			for (File f1: fileList) {
				System.out.println(f1.getName().substring(0, f1.getName().length()-5));
				System.out.println(files.size());
				files.add(f1.getName().substring(0, f1.getName().length()-5));
			}
			lv.setItems(files);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	private void delete(ActionEvent e) throws FileNotFoundException {
		int i = lv.getSelectionModel().getSelectedIndex();
		File f = new File(storeDir + lv.getSelectionModel().getSelectedItem() + ".list");
		f.delete();
		files.clear();
		File[] fileList = new File(storeDir).listFiles();
		for (File f1: fileList) {
			System.out.println(f1.getName().substring(0, f1.getName().length()-5));
			System.out.println(files.size());
			files.add(f1.getName().substring(0, f1.getName().length()-5));
		}
		lv.setItems(files);

	}


}
