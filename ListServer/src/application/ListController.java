package application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import res.Contact;

public class ListController extends Application implements Serializable {

	private static Stage mainStage;
	public static ObservableList<Contact> contacts = FXCollections.observableArrayList();
	private static ListView lv;
	public static String name;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		
		primaryStage.setTitle("ListServer");
		primaryStage.setResizable(false);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ListView.fxml"));
			Scene scene = new Scene(root);

			lv = (ListView) scene.lookup("#listView");
			try {
				readApp();
			} catch (Exception e) {
				e.printStackTrace();
			}
			lv.setItems(contacts);
			
			primaryStage.setOnCloseRequest(e -> {
				try {
					writeApp((Object[]) contacts.toArray());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        Platform.exit();
		        System.exit(0);
		    });
			
			((Text) scene.lookup("#listName")).setText(name);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mainStage = primaryStage;
		
	}
	
	public void setName(String na) {
		name = na;
	}
	
	public String getName() {
		return name;
	}
	
	@FXML
	private void back (ActionEvent e) {
		try {
			writeApp(contacts.toArray());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		(new Main()).start(mainStage);
	}

	@FXML
	private void select (ActionEvent e) {
		try {
			writeApp(contacts.toArray());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			(new ListSelectorController()).start(mainStage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@FXML
	private void add(ActionEvent e) {
		String n="", e2="";
		
		while(n.equals("")) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Contact Name");
			dialog.setHeaderText("Contact Name:");
			dialog.showAndWait();
			n=dialog.getEditor().getText();
		}

		if (n == null) {
			return;
		}
		while(e2.equals("")) {
			TextInputDialog dialog2 = new TextInputDialog();
			dialog2.setTitle("Contact Email");
			dialog2.setHeaderText("Contact Email:");
			dialog2.showAndWait();
			e2=dialog2.getEditor().getText();
		}
		
		contacts.add(new Contact(n,e2));
		
	}
	
	@FXML
	private void delete(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText("Are you sure you would like to delete this contact?");
		alert.setContentText("You can not undo this action");
		Optional<ButtonType> con = alert.showAndWait();
		
		
		if(con.get() == ButtonType.OK) {
			int i = lv.getSelectionModel().getSelectedIndex();
			if (i == -1) {
				Alert dialog = new Alert(AlertType.INFORMATION);
				dialog.setTitle("No Contact Selected");
				dialog.setHeaderText("Please select a contact and try again");
				dialog.showAndWait();
			} else
				contacts.remove(i);
		}
	}
	

	/**
	 * This method writes a list of current users out to a file for later use
	 * @param ac
	 * @throws IOException
	 */
	
	public static void writeApp(Object[] lc) throws IOException {
		String storeDir = "src"+File.separator+"albums"+File.separator;
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + name + ".list"));
		oos.writeObject(lc);
		System.out.println("Written");
	}
	
	/**
	 * This method reads the list of current users from a file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public static void readApp() throws IOException, ClassNotFoundException {
		String storeFile = "src"+File.separator+"albums"+File.separator+name+".list";
		
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeFile));
		Object[] c = (Object[]) ois.readObject();
		
		ObservableList<Contact> co = FXCollections.observableArrayList();
		
		for (Object c1: c) {
			co.add((Contact) c1);
		}
		
		contacts.clear();
		contacts.addAll(co);
	} 

}
