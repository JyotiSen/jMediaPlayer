package jmediaplayer;

import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author OMPRAKASH SEN
 */
public class FXMLDocumentController implements Initializable {
    
    
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser filechooser = new FileChooser();
        
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file(*.mp4)","*.mp4");
        filechooser.getExtensionFilters().add(filter);
        File file = filechooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        if(filePath != null)
        {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
            
            slider.setValue(mediaPlayer.getVolume()*100);
            slider.valueProperty().addListener((javafx.beans.Observable observable) -> {
                mediaPlayer.setVolume(slider.getValue()/100);
            });
            
           
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() { 
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)  {
                      seekSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                    seekSlider.setValue(newValue.toSeconds());
                
                }
            });
            
            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
            });
            
            
            
            mediaPlayer.play();
        }
        
        
    }
    
    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }
    
    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    }
    
    @FXML
    private void fastVideo(ActionEvent event){
       mediaPlayer.setRate(1.5); 
    }
    
    @FXML
    private void fasterVideo(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    
    @FXML
    private void slowVideo(ActionEvent event){
        mediaPlayer.setRate(0.75);
    }
    
    @FXML
    private void slowerVideo(ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    
    @FXML
    private void exitVideo(ActionEvent event){
        System.exit(0);
    }
    
    @FXML
    private void replayButton(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getStartTime());
    }
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
