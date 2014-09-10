package tree;

import congcrete.Year;
import application.main.FXMLDocumentController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class TreeCellFactory extends TreeCell<String> {
	 
    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();
    private boolean used = false ;

    public TreeCellFactory() {
        MenuItem addMenuItem = new MenuItem("Add Employee");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                TreeItem newEmployee = 
                    new TreeItem<String>("New Employee");
                        getTreeItem().getChildren().add(newEmployee);
            }
        });
    }
    
    /*
    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }
     
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }
	*/
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        
       
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            
            	setText(getString());
                setGraphic(getTreeItem().getGraphic());
                
                /*
                if (
                    !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                ){
                    setContextMenu(addMenu);
                }
                */
            
        }
    }
    
    
    /*
    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });  
        
    }
	*/
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}