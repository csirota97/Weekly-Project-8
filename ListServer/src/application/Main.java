package application;
	
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import res.Message;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;


public class Main extends Application implements Serializable {
	private static Stage mainStage;
	private static Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("ListServer");
		primaryStage.setResizable(false);
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("EmailView.fxml"));
			scene = new Scene(root);
			
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
	public void list(ActionEvent e) { 

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Did You Save?");
		alert.setHeaderText("Make Sure To Save Any Changes Before Leaving This Page");
		Optional<ButtonType> con = alert.showAndWait();
		
		
		if(con.get() == ButtonType.OK) {
			try {
				(new ListController()).start(mainStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void save(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialFileName(((TextField) scene.lookup("#subject")).getText().replace(' ', '_'));
        File file = fileChooser.showSaveDialog(mainStage);
        if (!file.getAbsolutePath().endsWith(".email"))
        file = new File(file.getAbsolutePath()+".email");
        if (file != null) {
            try {
    			ObjectOutputStream oos = new ObjectOutputStream(
    			new FileOutputStream(file.getPath()));
    			oos.writeObject(new Message((String) ((TextField) scene.lookup("#subject")).getText(),(String) ((TextArea) scene.lookup("#body")).getText()));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
		
	}
	@FXML
	public void cancel(ActionEvent e) {
        Platform.exit();
        System.exit(0);
	}

	@FXML
	public void setup(ActionEvent e) throws FileNotFoundException, IOException {
		String user = "", pass = "";
		
		TextInputDialog tid = new TextInputDialog();
		tid.setTitle("Username");
		tid.setHeaderText("Username");
		tid.setContentText("GMAIL USERNAME");
		if(!tid.showAndWait().equals(Optional.empty())) {
			user = tid.getEditor().getText();
		
			TextInputDialog tid2 = new TextInputDialog();
			tid2.setTitle("Password");
			tid2.setHeaderText("Password");
			tid2.setContentText("GMAIL Password");
			if(!tid2.showAndWait().equals(Optional.empty())) {
				pass = tid2.getEditor().getText();
				
				String fileString = "EMAIL_ADDRESS = '" + user + "'\n" + 
						"PASSWORD = '" + pass + "'";
				user = "";
				pass = "";
				String storeDir = "src"+File.separator+"py"+File.separator+"config.py";
				FileWriter fw = new FileWriter(storeDir);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(fileString);
				bw.close();
				fileString = "";
			}
		}
	}
	
	@FXML
	public void open(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showOpenDialog(mainStage);
        
        if (file != null) {
            try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file.getPath()));

				System.out.println(3);
				Message m = (Message) ois.readObject();
				((TextField) scene.lookup("#subject")).setText(m.getSubject());
				((TextArea) scene.lookup("#body")).setText(m.getMessage());
            } catch (IOException ex) {
                System.out.println(ex);
				System.out.println(2);
            } catch (ClassNotFoundException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				System.out.println(1);
			}
        }
	}
	@FXML
	public void send(ActionEvent e) throws IOException {
		String addresses = "addresses = [";
		String subject = "subject = ";
		String message = "message = ";
		
		for (int i = 0; i < ListController.contacts.size(); i++) {
			addresses = addresses + "'" + ListController.contacts.get(i).getEmail() + "'";
			if (i != ListController.contacts.size()-1)
				addresses = addresses + ',';
		}
		addresses = addresses + ']';
		
		String field = ((TextField) scene.lookup("#subject")).getText();
		field = field.replaceAll("\"", "\" + '\"' + \"");
		subject = subject + '"' + field + '"';

		field = ((TextArea) scene.lookup("#body")).getText();
		field = field.replaceAll("\"", "\" + '\"' + \"");
		message = message + '"' + field + '"';
		
		String fileString = addresses + '\n' + subject + '\n' + message;
		System.out.println(fileString);

		String storeDir = "src"+File.separator+"py"+File.separator+"emails.py";
		FileWriter fw = new FileWriter(storeDir);
		BufferedWriter bw = new BufferedWriter(fw);
		

		bw.write(fileString);
		bw.close();
		
		Process p = Runtime.getRuntime().exec("python src"+ File.separator + "py" + File.separator + "sendmail.py");
		
	}
	
	public static void main(String[] args) {
		ListController.name="default";
		launch(args);
	}
	

	/**
	 * This method writes a list of current users out to a file for later use
	 * @param ac
	 * @throws IOException
	 */
	
	public static void writeApp(Message ac) throws IOException {
			
	}
	
	/**
	 * This method reads the list of current users from a file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
/*	public static void readApp() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + storeFile));
		AdminController ac = new AdminController();
		User[] users = (User[]) ois.readObject();
		
		for (int i = 0; i < users.length; i++) {
			ac.data.add(users[i]);
		}
		
		sort();
		
		System.out.println(ac.toString());
	} 
	
	
	*/
}
