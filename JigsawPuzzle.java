/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.net.URL;
import java.util.Random;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class Sound extends Object {// 背景音樂控制

	private Media media;
	private MediaPlayer mediaPlayer;
	public void play() {
		mediaPlayer.play(); 
	}

	public void pause() {
		mediaPlayer.pause();
	}
        
	public void stop() {
		mediaPlayer.stop();
	}

	public void close() {
		mediaPlayer.dispose();
		System.gc(); 
	}
        public void loop() {
		setPlayCount(MediaPlayer.INDEFINITE);// this的方法
		play();// this的方法
	}

	public double getNewTime() {
		return mediaPlayer.getCurrentTime().toSeconds();
	}

	public void setVolume(double v) {
		mediaPlayer.setVolume(v);
	}

	public void setPlayCount(int count) {
		mediaPlayer.setCycleCount(count);
	}

	public Sound(String URL, boolean isAutoPlay) {
		this.media = new Media(URL);
		this.mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(isAutoPlay);
	}
        
	public Sound(URL URL, boolean isAutoPlay) {
		this(URL.toString(), isAutoPlay);
	}

	public Sound(File file, boolean isAutoPlay) {
		this(file.toURI().toString(), isAutoPlay);
	}

}

public class project_1 extends Application {//主要程式碼
    public int m=(int) (Math.random() * 8);
    ImageView[] imageViews = new ImageView[9];
    private Sound sound;
    public void start(Stage primaryStage)throws Exception {
        sound = new Sound("file:///C://Users//hyyan//Downloads/1.mp3", false);//使用URL讀取本機的檔案
	 sound.loop();//開始撥放音樂
        
        GridPane pane = new GridPane();
        Image image2 = new Image("File:C:\\Users\\hyyan\\Downloads/test.jpg");
        for (int i = 0; i < 9; i++) {
            imageViews[i] = new ImageView(image2);
            imageViews[i].setOnMouseClicked(new myevent());
        }
        for (int i = 0, k = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++, k++) {
                imageViews[k].setViewport(new Rectangle2D(250 * j, 250 * i, 250, 250)); // 切割圖片分配給圖片陣列
            }
        }
        int[] n = random();
        pane.add(imageViews[n[0]], 0, 0);
        pane.add(imageViews[n[1]], 1, 0);
        pane.add(imageViews[n[2]], 2, 0);
        pane.add(imageViews[n[3]], 0, 1);
        pane.add(imageViews[n[4]], 1, 1);
        pane.add(imageViews[n[5]], 2, 1);
        pane.add(imageViews[n[6]], 0, 2);
        pane.add(imageViews[n[7]], 1, 2);
        pane.add(imageViews[n[8]], 2, 2);

        pane.setGridLinesVisible(true);
        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("拼圖");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public int[] random() { // 生成8個不重複的逆序數為偶數的數字,這樣拼圖才有解
        int[] ran = new int[9];
        while (iso(ran) == false) {
            ran = random_num();
        }
        return ran;

    }

    public int[] random_num() { // 生成9個不重複數
        int r[] = new int[9];
        Random random = new Random();
        for (int i = 0; i < 9; ++i) {
            r[i] = random.nextInt(9);
            for (int j = 0; j < i; ++j) {
                while (r[i] == r[j]) {
                    i--;
                    break;
                }
            }
        }
        return r;
    }

    public boolean iso(int[] num) {// 判斷逆序數是否為偶數
        int sum = 0;
        for (int i = 0; i <= 6; ++i) {
            for (int j = i; j <= 7; j++) {
                if (num[i] > num[j]) {
                    sum++;
                }
            }
        }
        if ((sum % 2) == 0 && sum != 0) {
            return true;
        }
        return false;
    }

    public void swapimg(ImageView i1, ImageView i2) {//交換拼圖
        int row1 = GridPane.getRowIndex(i1);
        int colu1 = GridPane.getColumnIndex(i1);
        int row2 = GridPane.getRowIndex(i2);
        int colu2 = GridPane.getColumnIndex(i2);
        GridPane.setRowIndex(i1, row2);
        GridPane.setColumnIndex(i1, colu2);
        GridPane.setRowIndex(i2, row1);
        GridPane.setColumnIndex(i2, colu1);
    }
    public boolean issucc(ImageView[] imageViews) {//判斷拼圖是否完成
        for (int i = 0; i < 9; ++i) {
            if (i != 3 * GridPane.getRowIndex(imageViews[i]) + GridPane.getColumnIndex(imageViews[i])) {
                return false;
            }
        }
        return true;
    }

    class myevent implements EventHandler<MouseEvent> {//滑鼠點擊交換事件

        @Override
        public void handle(MouseEvent e) {
            ImageView img = (ImageView) e.getSource();
            int x = (int) e.getX();
            int y = (int) e.getY();
            swapimg(img, imageViews[m]);
            if (issucc(imageViews)) { // 判斷是否拼成功
                Alert alert = new Alert(AlertType.INFORMATION, "恭喜你，拼圖成功！");
                alert.show();
                sound.stop();//音樂停止撥放
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
