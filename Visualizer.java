import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import movements.*;

//FORMAT OF INT.TXT ANIME FILE FOR A TIME STAMP IS AS FOLLOWS:

//1 >>file number
//        1.0,1.0,1.0,1.0 >>background fill
//        GROUP 1 >> group number label - pretty useless but whatevsies
//        COORDS [Point2D [x = 4.0, y = 4.0], Point2D [x = 4.0, y = 12.0], Point2D [x = 4.0, y = 20.0], Point2D [x = 4.0, y = 28.0], Point2D [x = 4.0, y = 36.0], Point2D [x = 4.0, y = 44.0], Point2D [x = 4.0, y = 52.0], Point2D [x = 4.0, y = 60.0], Point2D [x = 4.0, y = 68.0], Point2D [x = 4.0, y = 76.0], Point2D [x = 4.0, y = 84.0], Point2D [x = 4.0, y = 92.0], Point2D [x = 4.0, y = 100.0], Point2D [x = 4.0, y = 108.0], Point2D [x = 4.0, y = 116.0], Point2D [x = 4.0, y = 124.0], Point2D [x = 4.0, y = 132.0], Point2D [x = 4.0, y = 140.0], Point2D [x = 4.0, y = 148.0], Point2D [x = 4.0, y = 156.0], Point2D [x = 4.0, y = 164.0], Point2D [x = 4.0, y = 172.0], Point2D [x = 4.0, y = 180.0], Point2D [x = 4.0, y = 188.0], Point2D [x = 4.0, y = 196.0], Point2D [x = 4.0, y = 204.0], Point2D [x = 4.0, y = 212.0], Point2D [x = 4.0, y = 220.0], Point2D [x = 4.0, y = 228.0], Point2D [x = 4.0, y = 236.0], Point2D [x = 4.0, y = 244.0], Point2D [x = 4.0, y = 252.0], Point2D [x = 4.0, y = 260.0], Point2D [x = 4.0, y = 268.0], Point2D [x = 4.0, y = 276.0], Point2D [x = 4.0, y = 284.0], Point2D [x = 4.0, y = 292.0], Point2D [x = 4.0, y = 300.0], Point2D [x = 4.0, y = 308.0], Point2D [x = 4.0, y = 316.0], Point2D [x = 4.0, y = 324.0], Point2D [x = 4.0, y = 332.0], Point2D [x = 4.0, y = 340.0], Point2D [x = 4.0, y = 348.0], Point2D [x = 4.0, y = 356.0], Point2D [x = 4.0, y = 364.0], Point2D [x = 4.0, y = 372.0], Point2D [x = 4.0, y = 380.0], Point2D [x = 4.0, y = 388.0], Point2D [x = 4.0, y = 396.0], Point2D [x = 4.0, y = 404.0], Point2D [x = 4.0, y = 412.0], Point2D [x = 4.0, y = 420.0], Point2D [x = 4.0, y = 428.0], Point2D [x = 12.0, y = 4.0], Point2D [x = 12.0, y = 12.0], Point2D [x = 12.0, y = 20.0], Point2D [x = 12.0, y = 28.0], Point2D [x = 12.0, y = 36.0], Point2D [x = 12.0, y = 44.0], Point2D [x = 12.0, y = 52.0], Point2D [x = 12.0, y = 60.0], Point2D [x = 12.0, y = 68.0], Point2D [x = 12.0, y = 76.0], Point2D [x = 12.0, y = 84.0], Point2D [x = 12.0, y = 92.0], Point2D [x = 12.0, y = 100.0], Point2D [x = 12.0, y = 108.0], Point2D [x = 12.0, y = 116.0], Point2D [x = 12.0, y = 124.0], Point2D [x = 12.0, y = 132.0], Point2D [x = 12.0, y = 140.0], Point2D [x = 12.0, y = 148.0], Point2D [x = 12.0, y = 156.0], Point2D [x = 12.0, y = 164.0], Point2D [x = 12.0, y = 172.0], Point2D [x = 12.0, y = 180.0], Point2D [x = 12.0, y = 188.0], Point2D [x = 12.0, y = 196.0], Point2D [x = 12.0, y = 204.0], Point2D [x = 12.0, y = 212.0], Point2D [x = 12.0, y = 220.0], Point2D [x = 12.0, y = 228.0], Point2D [x = 12.0, y = 236.0], Point2D [x = 12.0, y = 244.0], Point2D [x = 12.0, y = 252.0], Point2D [x = 12.0, y = 260.0], Point2D [x = 12.0, y = 268.0], Point2D [x = 12.0, y = 276.0], Point2D [x = 12.0, y = 284.0], Point2D [x = 12.0, y = 292.0], Point2D [x = 12.0, y = 300.0], Point2D [x = 12.0, y = 308.0], Point2D [x = 12.0, y = 316.0], Point2D [x = 12.0, y = 324.0], Point2D [x = 12.0, y = 332.0], Point2D [x = 12.0, y = 340.0], Point2D [x = 12.0, y = 348.0], Point2D [x = 12.0, y = 356.0], Point2D [x = 12.0, y = 364.0], Point2D [x = 12.0, y = 372.0], Point2D [x = 12.0, y = 380.0], Point2D [x = 12.0, y = 388.0], Point2D [x = 12.0, y = 396.0], Point2D [x = 12.0, y = 404.0], Point2D [x = 12.0, y = 412.0], Point2D [x = 12.0, y = 420.0], Point2D [x = 12.0, y = 428.0], Point2D [x = 20.0, y = 4.0], Point2D [x = 20.0, y = 12.0], Point2D [x = 20.0, y = 20.0], Point2D [x = 20.0, y = 28.0], Point2D [x = 20.0, y = 36.0], Point2D [x = 20.0, y = 44.0], Point2D [x = 20.0, y = 52.0], Point2D [x = 20.0, y = 60.0], Point2D [x = 20.0, y = 68.0], Point2D [x = 20.0, y = 76.0], Point2D [x = 20.0, y = 84.0], Point2D [x = 20.0, y = 92.0], Point2D [x = 20.0, y = 100.0], Point2D [x = 20.0, y = 108.0], Point2D [x = 20.0, y = 116.0], Point2D [x = 20.0, y = 124.0], Point2D [x = 20.0, y = 132.0], Point2D [x = 20.0, y = 140.0], Point2D [x = 20.0, y = 148.0], Point2D [x = 20.0, y = 156.0], Point2D [x = 20.0, y = 164.0], Point2D [x = 20.0, y = 172.0], Point2D [x = 20.0, y = 180.0], Point2D [x = 20.0, y = 188.0], Point2D [x = 20.0, y = 196.0], Point2D [x = 20.0, y = 204.0], Point2D [x = 20.0, y = 212.0], Point2D [x = 20.0, y = 220.0], Point2D [x = 20.0, y = 228.0], Point2D [x = 20.0, y = 236.0], Point2D [x = 20.0, y = 244.0], Point2D [x = 20.0, y = 252.0], Point2D [x = 20.0, y = 260.0], Point2D [x = 20.0, y = 268.0], Point2D [x = 20.0, y = 276.0], Point2D [x = 20.0, y = 284.0], Point2D [x = 20.0, y = 292.0], Point2D [x = 20.0, y = 300.0], Point2D [x = 20.0, y = 308.0], Point2D [x = 20.0, y = 316.0], Point2D [x = 20.0, y = 324.0], Point2D [x = 20.0, y = 332.0], Point2D [x = 20.0, y = 340.0], Point2D [x = 20.0, y = 348.0], Point2D [x = 20.0, y = 356.0], Point2D [x = 20.0, y = 364.0], Point2D [x = 20.0, y = 372.0], Point2D [x = 20.0, y = 380.0], Point2D [x = 20.0, y = 388.0], Point2D [x = 20.0, y = 396.0], Point2D [x = 20.0, y = 404.0], Point2D [x = 20.0, y = 412.0], Point2D [x = 20.0, y = 420.0], Point2D [x = 20.0, y = 428.0], Point2D [x = 28.0, y = 4.0], Point2D [x = 28.0, y = 12.0], Point2D [x = 28.0, y = 20.0], Point2D [x = 28.0, y = 28.0], Point2D [x = 28.0, y = 36.0], Point2D [x = 28.0, y = 44.0], Point2D [x = 28.0, y = 52.0], Point2D [x = 28.0, y = 60.0], Point2D [x = 28.0, y = 68.0], Point2D [x = 28.0, y = 76.0], Point2D [x = 28.0, y = 84.0], Point2D [x = 28.0, y = 92.0], Point2D [x = 28.0, y = 100.0], Point2D [x = 28.0, y = 108.0], Point2D [x = 28.0, y = 116.0], Point2D [x = 28.0, y = 124.0], Point2D [x = 28.0, y = 132.0], Point2D [x = 28.0, y = 140.0], Point2D [x = 28.0, y = 148.0], Point2D [x = 28.0, y = 156.0], Point2D [x = 28.0, y = 164.0], Point2D [x = 28.0, y = 172.0], Point2D [x = 28.0, y = 180.0], Point2D [x = 28.0, y = 188.0], Point2D [x = 28.0, y = 196.0], Point2D [x = 28.0, y = 204.0], Point2D [x = 28.0, y = 212.0], Point2D [x = 28.0, y = 220.0], Point2D [x = 28.0, y = 228.0], Point2D [x = 28.0, y = 236.0], Point2D [x = 28.0, y = 244.0], Point2D [x = 28.0, y = 252.0], Point2D [x = 28.0, y = 260.0], Point2D [x = 28.0, y = 268.0], Point2D [x = 28.0, y = 276.0], Point2D [x = 28.0, y = 284.0], Point2D [x = 28.0, y = 292.0], Point2D [x = 28.0, y = 300.0], Point2D [x = 28.0, y = 308.0], Point2D [x = 28.0, y = 316.0], Point2D [x = 28.0, y = 324.0], Point2D [x = 28.0, y = 332.0], Point2D [x = 28.0, y = 340.0], Point2D [x = 28.0, y = 348.0], Point2D [x = 28.0, y = 356.0], Point2D [x = 28.0, y = 364.0], Point2D [x = 28.0, y = 372.0], Point2D [x = 28.0, y = 380.0], Point2D [x = 28.0, y = 388.0], Point2D [x = 28.0, y = 396.0], Point2D [x = 28.0, y = 404.0], Point2D [x = 28.0, y = 412.0], Point2D [x = 28.0, y = 420.0], Point2D [x = 28.0, y = 428.0], Point2D [x = 36.0, y = 4.0], Point2D [x = 36.0, y = 12.0], Point2D [x = 36.0, y = 20.0], Point2D [x = 36.0, y = 28.0], Point2D [x = 36.0, y = 36.0], Point2D [x = 36.0, y = 44.0], Point2D [x = 36.0, y = 52.0], Point2D [x = 36.0, y = 60.0], Point2D [x = 36.0, y = 68.0], Point2D [x = 36.0, y = 76.0], Point2D [x = 36.0, y = 84.0], Point2D [x = 36.0, y = 92.0], Point2D [x = 36.0, y = 100.0], Point2D [x = 36.0, y = 108.0], Point2D [x = 36.0, y = 116.0], Point2D [x = 36.0, y = 124.0], Point2D [x = 36.0, y = 132.0], Point2D [x = 36.0, y = 140.0], Point2D [x = 36.0, y = 148.0], Point2D [x = 36.0, y = 156.0], Point2D [x = 36.0, y = 164.0], Point2D [x = 36.0, y = 172.0], Point2D [x = 36.0, y = 180.0], Point2D [x = 36.0, y = 188.0], Point2D [x = 36.0, y = 196.0], Point2D [x = 36.0, y = 204.0], Point2D [x = 36.0, y = 212.0], Point2D [x = 36.0, y = 220.0], Point2D [x = 36.0, y = 228.0], Point2D [x = 36.0, y = 236.0], Point2D [x = 36.0, y = 244.0], Point2D [x = 36.0, y = 252.0], Point2D [x = 36.0, y = 260.0], Point2D [x = 36.0, y = 268.0], Point2D [x = 36.0, y = 276.0], Point2D [x = 36.0, y = 284.0], Point2D [x = 36.0, y = 292.0], Point2D [x = 36.0, y = 300.0], Point2D [x = 36.0, y = 308.0], Point2D [x = 36.0, y = 316.0], Point2D [x = 36.0, y = 324.0], Point2D [x = 36.0, y = 332.0], Point2D [x = 36.0, y = 340.0], Point2D [x = 36.0, y = 348.0], Point2D [x = 36.0, y = 356.0], Point2D [x = 36.0, y = 364.0], Point2D [x = 36.0, y = 372.0], Point2D [x = 36.0, y = 380.0], Point2D [x = 36.0, y = 388.0], Point2D [x = 36.0, y = 396.0], Point2D [x = 36.0, y = 404.0], Point2D [x = 36.0, y = 412.0], Point2D [x = 36.0, y = 420.0], Point2D [x = 36.0, y = 428.0], Point2D [x = 44.0, y = 4.0], Point2D [x = 44.0, y = 12.0], Point2D [x = 44.0, y = 20.0], Point2D [x = 44.0, y = 28.0], Point2D [x = 44.0, y = 36.0], Point2D [x = 44.0, y = 44.0], Point2D [x = 44.0, y = 52.0], Point2D [x = 44.0, y = 60.0], Point2D [x = 44.0, y = 68.0], Point2D [x = 44.0, y = 76.0], Point2D [x = 44.0, y = 84.0], Point2D [x = 44.0, y = 92.0], Point2D [x = 44.0, y = 100.0], Point2D [x = 44.0, y = 108.0], Point2D [x = 44.0, y = 116.0], Point2D [x = 44.0, y = 124.0], Point2D [x = 44.0, y = 132.0], Point2D [x = 44.0, y = 140.0], Point2D [x = 44.0, y = 148.0], Point2D [x = 44.0, y = 156.0], Point2D [x = 44.0, y = 164.0], Point2D [x = 44.0, y = 172.0], Point2D [x = 44.0, y = 180.0], Point2D [x = 44.0, y = 188.0], Point2D [x = 44.0, y = 196.0], Point2D [x = 44.0, y = 204.0], Point2D [x = 44.0, y = 212.0], Point2D [x = 44.0, y = 220.0], Point2D [x = 44.0, y = 228.0], Point2D [x = 44.0, y = 236.0], Point2D [x = 44.0, y = 244.0], Point2D [x = 44.0, y = 252.0], Point2D [x = 44.0, y = 260.0], Point2D [x = 44.0, y = 268.0], Point2D [x = 44.0, y = 276.0], Point2D [x = 44.0, y = 284.0], Point2D [x = 44.0, y = 292.0], Point2D [x = 44.0, y = 300.0], Point2D [x = 44.0, y = 308.0], Point2D [x = 44.0, y = 316.0], Point2D [x = 44.0, y = 324.0], Point2D [x = 44.0, y = 332.0], Point2D [x = 44.0, y = 340.0], Point2D [x = 44.0, y = 348.0], Point2D [x = 44.0, y = 356.0], Point2D [x = 44.0, y = 364.0], Point2D [x = 44.0, y = 372.0], Point2D [x = 44.0, y = 380.0], Point2D [x = 44.0, y = 388.0], Point2D [x = 44.0, y = 396.0], Point2D [x = 44.0, y = 404.0], Point2D [x = 44.0, y = 412.0], Point2D [x = 44.0, y = 420.0], Point2D [x = 44.0, y = 428.0], Point2D [x = 52.0, y = 4.0], Point2D [x = 52.0, y = 12.0], Point2D [x = 52.0, y = 20.0], Point2D [x = 52.0, y = 28.0], Point2D [x = 52.0, y = 36.0], Point2D [x = 52.0, y = 44.0], Point2D [x = 52.0, y = 52.0], Point2D [x = 52.0, y = 60.0], Point2D [x = 52.0, y = 68.0], Point2D [x = 52.0, y = 76.0], Point2D [x = 52.0, y = 84.0], Point2D [x = 52.0, y = 92.0], Point2D [x = 52.0, y = 100.0], Point2D [x = 52.0, y = 108.0], Point2D [x = 52.0, y = 116.0], Point2D [x = 52.0, y = 124.0], Point2D [x = 52.0, y = 132.0], Point2D [x = 52.0, y = 140.0], Point2D [x = 52.0, y = 148.0], Point2D [x = 52.0, y = 156.0], Point2D [x = 52.0, y = 164.0], Point2D [x = 52.0, y = 172.0], Point2D [x = 52.0, y = 180.0], Point2D [x = 52.0, y = 188.0], Point2D [x = 52.0, y = 196.0], Point2D [x = 52.0, y = 204.0], Point2D [x = 52.0, y = 212.0], Point2D [x = 52.0, y = 220.0], Point2D [x = 52.0, y = 228.0], Point2D [x = 52.0, y = 236.0], Point2D [x = 52.0, y = 244.0], Point2D [x = 52.0, y = 252.0], Point2D [x = 52.0, y = 260.0], Point2D [x = 52.0, y = 268.0], Point2D [x = 52.0, y = 276.0], Point2D [x = 52.0, y = 284.0], Point2D [x = 52.0, y = 292.0], Point2D [x = 52.0, y = 300.0], Point2D [x = 52.0, y = 308.0], Point2D [x = 52.0, y = 316.0], Point2D [x = 52.0, y = 324.0], Point2D [x = 52.0, y = 332.0], Point2D [x = 52.0, y = 340.0], Point2D [x = 52.0, y = 348.0], Point2D [x = 52.0, y = 356.0], Point2D [x = 52.0, y = 364.0], Point2D [x = 52.0, y = 372.0], Point2D [x = 52.0, y = 380.0], Point2D [x = 52.0, y = 388.0], Point2D [x = 52.0, y = 396.0], Point2D [x = 52.0, y = 404.0], Point2D [x = 52.0, y = 412.0], Point2D [x = 52.0, y = 420.0], Point2D [x = 52.0, y = 428.0], Point2D [x = 60.0, y = 4.0], Point2D [x = 60.0, y = 12.0], Point2D [x = 60.0, y = 20.0], Point2D [x = 60.0, y = 28.0], Point2D [x = 60.0, y = 36.0], Point2D [x = 60.0, y = 44.0], Point2D [x = 60.0, y = 52.0], Point2D [x = 60.0, y = 60.0], Point2D [x = 60.0, y = 68.0], Point2D [x = 60.0, y = 76.0], Point2D [x = 60.0, y = 84.0], Point2D [x = 60.0, y = 92.0], Point2D [x = 60.0, y = 100.0], Point2D [x = 60.0, y = 108.0], Point2D [x = 60.0, y = 116.0], Point2D [x = 60.0, y = 124.0], Point2D [x = 60.0, y = 132.0], Point2D [x = 60.0, y = 140.0], Point2D [x = 60.0, y = 148.0], Point2D [x = 60.0, y = 156.0], Point2D [x = 60.0, y = 164.0], Point2D [x = 60.0, y = 172.0], Point2D [x = 60.0, y = 180.0], Point2D [x = 60.0, y = 188.0], Point2D [x = 60.0, y = 196.0], Point2D [x = 60.0, y = 204.0], Point2D [x = 60.0, y = 212.0], Point2D [x = 60.0, y = 220.0], Point2D [x = 60.0, y = 228.0], Point2D [x = 60.0, y = 236.0], Point2D [x = 60.0, y = 244.0], Point2D [x = 60.0, y = 252.0], Point2D [x = 60.0, y = 260.0], Point2D [x = 60.0, y = 268.0], Point2D [x = 60.0, y = 276.0], Point2D [x = 60.0, y = 284.0], Point2D [x = 60.0, y = 292.0], Point2D [x = 60.0, y = 300.0], Point2D [x = 60.0, y = 308.0], Point2D [x = 60.0, y = 316.0], Point2D [x = 60.0, y = 324.0], Point2D [x = 60.0, y = 332.0], Point2D [x = 60.0, y = 340.0], Point2D [x = 60.0, y = 348.0], Point2D [x = 60.0, y = 356.0], Point2D [x = 60.0, y = 364.0], Point2D [x = 60.0, y = 372.0], Point2D [x = 60.0, y = 380.0], Point2D [x = 60.0, y = 388.0], Point2D [x = 60.0, y = 396.0], Point2D [x = 60.0, y = 404.0], Point2D [x = 60.0, y = 412.0], Point2D [x = 60.0, y = 420.0], Point2D [x = 60.0, y = 428.0], Point2D [x = 68.0, y = 4.0], Point2D [x = 68.0, y = 12.0], Point2D [x = 68.0, y = 20.0], Point2D [x = 68.0, y = 28.0], Point2D [x = 68.0, y = 36.0], Point2D [x = 68.0, y = 44.0], Point2D [x = 68.0, y = 52.0], Point2D [x = 68.0, y = 60.0], Point2D [x = 68.0, y = 68.0], Point2D [x = 68.0, y = 76.0], Point2D [x = 68.0, y = 84.0], Point2D [x = 68.0, y = 92.0], Point2D [x = 68.0, y = 100.0], Point2D [x = 68.0, y = 108.0], Point2D [x = 68.0, y = 116.0], Point2D [x = 68.0, y = 124.0], Point2D [x = 68.0, y = 132.0], Point2D [x = 68.0, y = 140.0], Point2D [x = 68.0, y = 148.0], Point2D [x = 68.0, y = 156.0], Point2D [x = 68.0, y = 164.0], Point2D [x = 68.0, y = 172.0], Point2D [x = 68.0, y = 180.0], Point2D [x = 68.0, y = 188.0], Point2D [x = 68.0, y = 196.0], Point2D [x = 68.0, y = 204.0], Point2D [x = 68.0, y = 212.0], Point2D [x = 68.0, y = 220.0], Point2D [x = 68.0, y = 228.0], Point2D [x = 68.0, y = 236.0], Point2D [x = 68.0, y = 244.0], Point2D [x = 68.0, y = 252.0], Point2D [x = 68.0, y = 260.0], Point2D [x = 68.0, y = 268.0], Point2D [x = 68.0, y = 276.0], Point2D [x = 68.0, y = 284.0], Point2D [x = 68.0, y = 292.0], Point2D [x = 68.0, y = 300.0], Point2D [x = 68.0, y = 308.0], Point2D [x = 68.0, y = 316.0], Point2D [x = 68.0, y = 324.0], Point2D [x = 68.0, y = 332.0], Point2D [x = 68.0, y = 340.0], Point2D [x = 68.0, y = 348.0], Point2D [x = 68.0, y = 356.0], Point2D [x = 68.0, y = 364.0], Point2D [x = 68.0, y = 372.0], Point2D [x = 68.0, y = 380.0], Point2D [x = 68.0, y = 388.0], Point2D [x = 68.0, y = 396.0], Point2D [x = 68.0, y = 404.0], Point2D [x = 68.0, y = 412.0], Point2D [x = 68.0, y = 420.0], Point2D [x = 68.0, y = 428.0], Point2D [x = 76.0, y = 4.0], Point2D [x = 76.0, y = 12.0], Point2D [x = 76.0, y = 20.0], Point2D [x = 76.0, y = 28.0], Point2D [x = 76.0, y = 36.0], Point2D [x = 76.0, y = 44.0], Point2D [x = 76.0, y = 52.0], Point2D [x = 76.0, y = 60.0], Point2D [x = 76.0, y = 68.0], Point2D [x = 76.0, y = 76.0], Point2D [x = 76.0, y = 84.0], Point2D [x = 76.0, y = 92.0], Point2D [x = 76.0, y = 100.0], Point2D [x = 76.0, y = 108.0], Point2D [x = 76.0, y = 116.0], Point2D [x = 76.0, y = 124.0], Point2D [x = 76.0, y = 132.0], Point2D [x = 76.0, y = 140.0], Point2D [x = 76.0, y = 148.0], Point2D [x = 76.0, y = 156.0], Point2D [x = 76.0, y = 164.0], Point2D [x = 76.0, y = 172.0], Point2D [x = 76.0, y = 180.0], Point2D [x = 76.0, y = 188.0], Point2D [x = 76.0, y = 196.0], Point2D [x = 76.0, y = 204.0], Point2D [x = 76.0, y = 212.0], Point2D [x = 76.0, y = 220.0], Point2D [x = 76.0, y = 228.0], Point2D [x = 76.0, y = 236.0], Point2D [x = 76.0, y = 244.0], Point2D [x = 76.0, y = 252.0], Point2D [x = 76.0, y = 260.0], Point2D [x = 76.0, y = 268.0], Point2D [x = 76.0, y = 276.0], Point2D [x = 76.0, y = 284.0], Point2D [x = 76.0, y = 292.0], Point2D [x = 76.0, y = 300.0], Point2D [x = 76.0, y = 308.0], Point2D [x = 76.0, y = 316.0], Point2D [x = 76.0, y = 324.0], Point2D [x = 76.0, y = 332.0], Point2D [x = 76.0, y = 340.0], Point2D [x = 76.0, y = 348.0], Point2D [x = 76.0, y = 356.0], Point2D [x = 76.0, y = 364.0], Point2D [x = 76.0, y = 372.0], Point2D [x = 76.0, y = 380.0], Point2D [x = 76.0, y = 388.0], Point2D [x = 76.0, y = 396.0], Point2D [x = 76.0, y = 404.0], Point2D [x = 76.0, y = 412.0], Point2D [x = 76.0, y = 420.0], Point2D [x = 76.0, y = 428.0], Point2D [x = 84.0, y = 4.0], Point2D [x = 84.0, y = 12.0], Point2D [x = 84.0, y = 20.0], Point2D [x = 84.0, y = 28.0], Point2D [x = 84.0, y = 36.0], Point2D [x = 84.0, y = 44.0], Point2D [x = 84.0, y = 52.0], Point2D [x = 84.0, y = 60.0], Point2D [x = 84.0, y = 68.0], Point2D [x = 84.0, y = 76.0], Point2D [x = 84.0, y = 84.0], Point2D [x = 84.0, y = 92.0], Point2D [x = 84.0, y = 100.0], Point2D [x = 84.0, y = 108.0], Point2D [x = 84.0, y = 116.0], Point2D [x = 84.0, y = 124.0], Point2D [x = 84.0, y = 132.0], Point2D [x = 84.0, y = 140.0], Point2D [x = 84.0, y = 148.0], Point2D [x = 84.0, y = 156.0], Point2D [x = 84.0, y = 164.0], Point2D [x = 84.0, y = 172.0], Point2D [x = 84.0, y = 180.0], Point2D [x = 84.0, y = 188.0], Point2D [x = 84.0, y = 196.0], Point2D [x = 84.0, y = 204.0], Point2D [x = 84.0, y = 212.0], Point2D [x = 84.0, y = 220.0], Point2D [x = 84.0, y = 228.0], Point2D [x = 84.0, y = 236.0], Point2D [x = 84.0, y = 244.0], Point2D [x = 84.0, y = 252.0], Point2D [x = 84.0, y = 260.0], Point2D [x = 84.0, y = 268.0], Point2D [x = 84.0, y = 276.0], Point2D [x = 84.0, y = 284.0], Point2D [x = 84.0, y = 292.0], Point2D [x = 84.0, y = 300.0], Point2D [x = 84.0, y = 308.0], Point2D [x = 84.0, y = 316.0], Point2D [x = 84.0, y = 324.0], Point2D [x = 84.0, y = 332.0], Point2D [x = 84.0, y = 340.0], Point2D [x = 84.0, y = 348.0], Point2D [x = 84.0, y = 356.0], Point2D [x = 84.0, y = 364.0], Point2D [x = 84.0, y = 372.0], Point2D [x = 84.0, y = 380.0], Point2D [x = 84.0, y = 388.0], Point2D [x = 84.0, y = 396.0], Point2D [x = 84.0, y = 404.0], Point2D [x = 84.0, y = 412.0], Point2D [x = 84.0, y = 420.0], Point2D [x = 84.0, y = 428.0], Point2D [x = 92.0, y = 4.0], Point2D [x = 92.0, y = 12.0], Point2D [x = 92.0, y = 20.0], Point2D [x = 92.0, y = 28.0], Point2D [x = 92.0, y = 36.0], Point2D [x = 92.0, y = 44.0], Point2D [x = 92.0, y = 52.0], Point2D [x = 92.0, y = 60.0], Point2D [x = 92.0, y = 68.0], Point2D [x = 92.0, y = 76.0], Point2D [x = 92.0, y = 84.0], Point2D [x = 92.0, y = 92.0], Point2D [x = 92.0, y = 100.0], Point2D [x = 92.0, y = 108.0], Point2D [x = 92.0, y = 116.0], Point2D [x = 92.0, y = 124.0], Point2D [x = 92.0, y = 132.0], Point2D [x = 92.0, y = 140.0], Point2D [x = 92.0, y = 148.0], Point2D [x = 92.0, y = 156.0], Point2D [x = 92.0, y = 164.0], Point2D [x = 92.0, y = 172.0], Point2D [x = 92.0, y = 180.0], Point2D [x = 92.0, y = 188.0], Point2D [x = 92.0, y = 196.0], Point2D [x = 92.0, y = 204.0], Point2D [x = 92.0, y = 212.0], Point2D [x = 92.0, y = 220.0], Point2D [x = 92.0, y = 228.0], Point2D [x = 92.0, y = 236.0], Point2D [x = 92.0, y = 244.0], Point2D [x = 92.0, y = 252.0], Point2D [x = 92.0, y = 260.0], Point2D [x = 92.0, y = 268.0], Point2D [x = 92.0, y = 276.0], Point2D [x = 92.0, y = 284.0], Point2D [x = 92.0, y = 292.0], Point2D [x = 92.0, y = 300.0], Point2D [x = 92.0, y = 308.0], Point2D [x = 92.0, y = 316.0], Point2D [x = 92.0, y = 324.0], Point2D [x = 92.0, y = 332.0], Point2D [x = 92.0, y = 340.0], Point2D [x = 92.0, y = 348.0], Point2D [x = 92.0, y = 356.0], Point2D [x = 92.0, y = 364.0], Point2D [x = 92.0, y = 372.0], Point2D [x = 92.0, y = 380.0], Point2D [x = 92.0, y = 388.0], Point2D [x = 92.0, y = 396.0], Point2D [x = 92.0, y = 404.0], Point2D [x = 92.0, y = 412.0], Point2D [x = 92.0, y = 420.0], Point2D [x = 92.0, y = 428.0], Point2D [x = 100.0, y = 4.0], Point2D [x = 100.0, y = 12.0], Point2D [x = 100.0, y = 20.0], Point2D [x = 100.0, y = 28.0], Point2D [x = 100.0, y = 36.0], Point2D [x = 100.0, y = 44.0], Point2D [x = 100.0, y = 52.0], Point2D [x = 100.0, y = 60.0], Point2D [x = 100.0, y = 68.0], Point2D [x = 100.0, y = 76.0], Point2D [x = 100.0, y = 84.0], Point2D [x = 100.0, y = 92.0], Point2D [x = 100.0, y = 100.0], Point2D [x = 100.0, y = 108.0], Point2D [x = 100.0, y = 116.0], Point2D [x = 100.0, y = 124.0], Point2D [x = 100.0, y = 132.0], Point2D [x = 100.0, y = 140.0], Point2D [x = 100.0, y = 148.0], Point2D [x = 100.0, y = 156.0], Point2D [x = 100.0, y = 164.0], Point2D [x = 100.0, y = 172.0], Point2D [x = 100.0, y = 180.0], Point2D [x = 100.0, y = 188.0], Point2D [x = 100.0, y = 196.0], Point2D [x = 100.0, y = 204.0], Point2D [x = 100.0, y = 212.0], Point2D [x = 100.0, y = 220.0], Point2D [x = 100.0, y = 228.0], Point2D [x = 100.0, y = 236.0], Point2D [x = 100.0, y = 244.0], Point2D [x = 100.0, y = 252.0], Point2D [x = 100.0, y = 260.0], Point2D [x = 100.0, y = 268.0], Point2D [x = 100.0, y = 276.0], Point2D [x = 100.0, y = 284.0], Point2D [x = 100.0, y = 292.0], Point2D [x = 100.0, y = 300.0], Point2D [x = 100.0, y = 308.0], Point2D [x = 100.0, y = 316.0], Point2D [x = 100.0, y = 324.0], Point2D [x = 100.0, y = 332.0], Point2D [x = 100.0, y = 340.0], Point2D [x = 100.0, y = 348.0], Point2D [x = 100.0, y = 356.0], Point2D [x = 100.0, y = 364.0], Point2D [x = 100.0, y = 372.0], Point2D [x = 100.0, y = 380.0], Point2D [x = 100.0, y = 388.0], Point2D [x = 100.0, y = 396.0], Point2D [x = 100.0, y = 404.0], Point2D [x = 100.0, y = 412.0], Point2D [x = 100.0, y = 420.0], Point2D [x = 100.0, y = 428.0], Point2D [x = 108.0, y = 4.0], Point2D [x = 108.0, y = 12.0], Point2D [x = 108.0, y = 20.0], Point2D [x = 108.0, y = 28.0], Point2D [x = 108.0, y = 36.0], Point2D [x = 108.0, y = 44.0], Point2D [x = 108.0, y = 52.0], Point2D [x = 108.0, y = 60.0], Point2D [x = 108.0, y = 68.0], Point2D [x = 108.0, y = 76.0], Point2D [x = 108.0, y = 84.0], Point2D [x = 108.0, y = 92.0], Point2D [x = 108.0, y = 100.0], Point2D [x = 108.0, y = 108.0], Point2D [x = 108.0, y = 116.0], Point2D [x = 108.0, y = 124.0], Point2D [x = 108.0, y = 132.0], Point2D [x = 108.0, y = 140.0], Point2D [x = 108.0, y = 148.0], Point2D [x = 108.0, y = 156.0], Point2D [x = 108.0, y = 164.0], Point2D [x = 108.0, y = 172.0], Point2D [x = 108.0, y = 180.0], Point2D [x = 108.0, y = 188.0], Point2D [x = 108.0, y = 196.0], Point2D [x = 108.0, y = 204.0], Point2D [x = 108.0, y = 212.0], Point2D [x = 108.0, y = 220.0], Point2D [x = 108.0, y = 228.0], Point2D [x = 108.0, y = 236.0], Point2D [x = 108.0, y = 244.0], Point2D [x = 108.0, y = 252.0], Point2D [x = 108.0, y = 260.0], Point2D [x = 108.0, y = 268.0], Point2D [x = 108.0, y = 276.0], Point2D [x = 108.0, y = 284.0], Point2D [x = 108.0, y = 292.0], Point2D [x = 108.0, y = 300.0], Point2D [x = 108.0, y = 308.0], Point2D [x = 108.0, y = 316.0], Point2D [x = 108.0, y = 324.0], Point2D [x = 108.0, y = 332.0], Point2D [x = 108.0, y = 340.0], Point2D [x = 108.0, y = 348.0], Point2D [x = 108.0, y = 356.0], Point2D [x = 108.0, y = 364.0], Point2D [x = 108.0, y = 372.0], Point2D [x = 108.0, y = 380.0], Point2D [x = 108.0, y = 388.0], Point2D [x = 108.0, y = 396.0], Point2D [x = 108.0, y = 404.0], Point2D [x = 108.0, y = 412.0], Point2D [x = 108.0, y = 420.0], Point2D [x = 108.0, y = 428.0], Point2D [x = 116.0, y = 4.0], Point2D [x = 116.0, y = 12.0], Point2D [x = 116.0, y = 20.0], Point2D [x = 116.0, y = 28.0], Point2D [x = 116.0, y = 36.0], Point2D [x = 116.0, y = 44.0], Point2D [x = 116.0, y = 52.0], Point2D [x = 116.0, y = 60.0], Point2D [x = 116.0, y = 68.0], Point2D [x = 116.0, y = 76.0], Point2D [x = 116.0, y = 84.0], Point2D [x = 116.0, y = 92.0], Point2D [x = 116.0, y = 100.0], Point2D [x = 116.0, y = 108.0], Point2D [x = 116.0, y = 116.0], Point2D [x = 116.0, y = 124.0], Point2D [x = 116.0, y = 132.0], Point2D [x = 116.0, y = 140.0], Point2D [x = 116.0, y = 148.0], Point2D [x = 116.0, y = 156.0], Point2D [x = 116.0, y = 164.0], Point2D [x = 116.0, y = 172.0], Point2D [x = 116.0, y = 180.0], Point2D [x = 116.0, y = 188.0], Point2D [x = 116.0, y = 196.0], Point2D [x = 116.0, y = 204.0], Point2D [x = 116.0, y = 212.0], Point2D [x = 116.0, y = 220.0], Point2D [x = 116.0, y = 228.0], Point2D [x = 116.0, y = 236.0], Point2D [x = 116.0, y = 244.0], Point2D [x = 116.0, y = 252.0], Point2D [x = 116.0, y = 260.0], Point2D [x = 116.0, y = 268.0], Point2D [x = 116.0, y = 276.0], Point2D [x = 116.0, y = 284.0], Point2D [x = 116.0, y = 292.0], Point2D [x = 116.0, y = 300.0], Point2D [x = 116.0, y = 308.0], Point2D [x = 116.0, y = 316.0], Point2D [x = 116.0, y = 324.0], Point2D [x = 116.0, y = 332.0], Point2D [x = 116.0, y = 340.0], Point2D [x = 116.0, y = 348.0], Point2D [x = 116.0, y = 356.0], Point2D [x = 116.0, y = 364.0], Point2D [x = 116.0, y = 372.0], Point2D [x = 116.0, y = 380.0], Point2D [x = 116.0, y = 388.0], Point2D [x = 116.0, y = 396.0], Point2D [x = 116.0, y = 404.0], Point2D [x = 116.0, y = 412.0], Point2D [x = 116.0, y = 420.0], Point2D [x = 116.0, y = 428.0], Point2D [x = 124.0, y = 4.0], Point2D [x = 124.0, y = 12.0], Point2D [x = 124.0, y = 20.0], Point2D [x = 124.0, y = 28.0], Point2D [x = 124.0, y = 36.0], Point2D [x = 124.0, y = 44.0], Point2D [x = 124.0, y = 52.0], Point2D [x = 124.0, y = 60.0], Point2D [x = 124.0, y = 68.0], Point2D [x = 124.0, y = 76.0], Point2D [x = 124.0, y = 84.0], Point2D [x = 124.0, y = 92.0], Point2D [x = 124.0, y = 100.0], Point2D [x = 124.0, y = 108.0], Point2D [x = 124.0, y = 116.0], Point2D [x = 124.0, y = 124.0], Point2D [x = 124.0, y = 132.0], Point2D [x = 124.0, y = 140.0], Point2D [x = 124.0, y = 148.0], Point2D [x = 124.0, y = 156.0], Point2D [x = 124.0, y = 164.0], Point2D [x = 124.0, y = 172.0], Point2D [x = 124.0, y = 180.0], Point2D [x = 124.0, y = 188.0], Point2D [x = 124.0, y = 196.0], Point2D [x = 124.0, y = 204.0], Point2D [x = 124.0, y = 212.0], Point2D [x = 124.0, y = 220.0], Point2D [x = 124.0, y = 228.0], Point2D [x = 124.0, y = 236.0], Point2D [x = 124.0, y = 244.0], Point2D [x = 124.0, y = 252.0], Point2D [x = 124.0, y = 260.0], Point2D [x = 124.0, y = 268.0], Point2D [x = 124.0, y = 276.0], Point2D [x = 124.0, y = 284.0], Point2D [x = 124.0, y = 292.0], Point2D [x = 124.0, y = 300.0], Point2D [x = 124.0, y = 308.0], Point2D [x = 124.0, y = 316.0], Point2D [x = 124.0, y = 324.0], Point2D [x = 124.0, y = 332.0], Point2D [x = 124.0, y = 340.0], Point2D [x = 124.0, y = 348.0], Point2D [x = 124.0, y = 356.0], Point2D [x = 124.0, y = 364.0], Point2D [x = 124.0, y = 372.0], Point2D [x = 124.0, y = 380.0], Point2D [x = 124.0, y = 388.0], Point2D [x = 124.0, y = 396.0], Point2D [x = 124.0, y = 404.0], Point2D [x = 124.0, y = 412.0], Point2D [x = 124.0, y = 420.0], Point2D [x = 124.0, y = 428.0], Point2D [x = 132.0, y = 4.0], Point2D [x = 132.0, y = 12.0], Point2D [x = 132.0, y = 20.0], Point2D [x = 132.0, y = 28.0], Point2D [x = 132.0, y = 36.0], Point2D [x = 132.0, y = 44.0], Point2D [x = 132.0, y = 52.0], Point2D [x = 132.0, y = 60.0], Point2D [x = 132.0, y = 68.0], Point2D [x = 132.0, y = 76.0], Point2D [x = 132.0, y = 84.0], Point2D [x = 132.0, y = 92.0], Point2D [x = 132.0, y = 100.0], Point2D [x = 132.0, y = 108.0], Point2D [x = 132.0, y = 116.0], Point2D [x = 132.0, y = 124.0], Point2D [x = 132.0, y = 132.0], Point2D [x = 132.0, y = 140.0], Point2D [x = 132.0, y = 148.0], Point2D [x = 132.0, y = 156.0], Point2D [x = 132.0, y = 164.0], Point2D [x = 132.0, y = 172.0], Point2D [x = 132.0, y = 180.0], Point2D [x = 132.0, y = 188.0], Point2D [x = 132.0, y = 196.0], Point2D [x = 132.0, y = 204.0], Point2D [x = 132.0, y = 212.0], Point2D [x = 132.0, y = 220.0], Point2D [x = 132.0, y = 228.0], Point2D [x = 132.0, y = 236.0], Point2D [x = 132.0, y = 244.0], Point2D [x = 132.0, y = 252.0], Point2D [x = 132.0, y = 260.0], Point2D [x = 132.0, y = 268.0], Point2D [x = 132.0, y = 276.0], Point2D [x = 132.0, y = 284.0], Point2D [x = 132.0, y = 292.0], Point2D [x = 132.0, y = 300.0], Point2D [x = 132.0, y = 308.0], Point2D [x = 132.0, y = 316.0], Point2D [x = 132.0, y = 324.0], Point2D [x = 132.0, y = 332.0], Point2D [x = 132.0, y = 340.0], Point2D [x = 132.0, y = 348.0], Point2D [x = 132.0, y = 356.0], Point2D [x = 132.0, y = 364.0], Point2D [x = 132.0, y = 372.0], Point2D [x = 132.0, y = 380.0], Point2D [x = 132.0, y = 388.0], Point2D [x = 132.0, y = 396.0], Point2D [x = 132.0, y = 404.0], Point2D [x = 132.0, y = 412.0], Point2D [x = 132.0, y = 420.0], Point2D [x = 132.0, y = 428.0], Point2D [x = 140.0, y = 4.0], Point2D [x = 140.0, y = 12.0], Point2D [x = 140.0, y = 20.0], Point2D [x = 140.0, y = 28.0], Point2D [x = 140.0, y = 36.0], Point2D [x = 140.0, y = 44.0], Point2D [x = 140.0, y = 52.0], Point2D [x = 140.0, y = 60.0], Point2D [x = 140.0, y = 68.0], Point2D [x = 140.0, y = 76.0], Point2D [x = 140.0, y = 84.0], Point2D [x = 140.0, y = 92.0], Point2D [x = 140.0, y = 100.0], Point2D [x = 140.0, y = 108.0], Point2D [x = 140.0, y = 116.0], Point2D [x = 140.0, y = 124.0], Point2D [x = 140.0, y = 132.0], Point2D [x = 140.0, y = 140.0], Point2D [x = 140.0, y = 148.0], Point2D [x = 140.0, y = 156.0], Point2D [x = 140.0, y = 164.0], Point2D [x = 140.0, y = 172.0], Point2D [x = 140.0, y = 180.0], Point2D [x = 140.0, y = 188.0], Point2D [x = 140.0, y = 196.0], Point2D [x = 140.0, y = 204.0], Point2D [x = 140.0, y = 212.0], Point2D [x = 140.0, y = 220.0], Point2D [x = 140.0, y = 228.0], Point2D [x = 140.0, y = 236.0], Point2D [x = 140.0, y = 244.0], Point2D [x = 140.0, y = 252.0], Point2D [x = 140.0, y = 260.0], Point2D [x = 140.0, y = 268.0], Point2D [x = 140.0, y = 276.0], Point2D [x = 140.0, y = 284.0], Point2D [x = 140.0, y = 292.0], Point2D [x = 140.0, y = 300.0], Point2D [x = 140.0, y = 308.0], Point2D [x = 140.0, y = 316.0], Point2D [x = 140.0, y = 324.0], Point2D [x = 140.0, y = 332.0], Point2D [x = 140.0, y = 340.0], Point2D [x = 140.0, y = 348.0], Point2D [x = 140.0, y = 356.0], Point2D [x = 140.0, y = 364.0], Point2D [x = 140.0, y = 372.0], Point2D [x = 140.0, y = 380.0], Point2D [x = 140.0, y = 388.0], Point2D [x = 140.0, y = 396.0], Point2D [x = 140.0, y = 404.0], Point2D [x = 140.0, y = 412.0], Point2D [x = 140.0, y = 420.0], Point2D [x = 140.0, y = 428.0], Point2D [x = 148.0, y = 4.0], Point2D [x = 148.0, y = 12.0], Point2D [x = 148.0, y = 20.0], Point2D [x = 148.0, y = 28.0], Point2D [x = 148.0, y = 36.0], Point2D [x = 148.0, y = 44.0], Point2D [x = 148.0, y = 52.0], Point2D [x = 148.0, y = 60.0], Point2D [x = 148.0, y = 68.0], Point2D [x = 148.0, y = 76.0], Point2D [x = 148.0, y = 84.0], Point2D [x = 148.0, y = 92.0], Point2D [x = 148.0, y = 100.0], Point2D [x = 148.0, y = 108.0], Point2D [x = 148.0, y = 116.0], Point2D [x = 148.0, y = 124.0], Point2D [x = 148.0, y = 132.0], Point2D [x = 148.0, y = 140.0], Point2D [x = 148.0, y = 148.0], Point2D [x = 148.0, y = 156.0], Point2D [x = 148.0, y = 164.0], Point2D [x = 148.0, y = 172.0], Point2D [x = 148.0, y = 180.0], Point2D [x = 148.0, y = 188.0], Point2D [x = 148.0, y = 196.0], Point2D [x = 148.0, y = 204.0], Point2D [x = 148.0, y = 212.0], Point2D [x = 148.0, y = 220.0], Point2D [x = 148.0, y = 228.0], Point2D [x = 148.0, y = 236.0], Point2D [x = 148.0, y = 244.0], Point2D [x = 148.0, y = 252.0], Point2D [x = 148.0, y = 260.0], Point2D [x = 148.0, y = 268.0], Point2D [x = 148.0, y = 276.0], Point2D [x = 148.0, y = 284.0], Point2D [x = 148.0, y = 292.0], Point2D [x = 148.0, y = 300.0], Point2D [x = 148.0, y = 308.0], Point2D [x = 148.0, y = 316.0], Point2D [x = 148.0, y = 324.0], Point2D [x = 148.0, y = 332.0], Point2D [x = 148.0, y = 340.0], Point2D [x = 148.0, y = 348.0], Point2D [x = 148.0, y = 356.0], Point2D [x = 148.0, y = 364.0], Point2D [x = 148.0, y = 372.0], Point2D [x = 148.0, y = 380.0], Point2D [x = 148.0, y = 388.0], Point2D [x = 148.0, y = 396.0], Point2D [x = 148.0, y = 404.0], Point2D [x = 148.0, y = 412.0], Point2D [x = 148.0, y = 420.0], Point2D [x = 148.0, y = 428.0], Point2D [x = 156.0, y = 4.0], Point2D [x = 156.0, y = 12.0], Point2D [x = 156.0, y = 20.0], Point2D [x = 156.0, y = 28.0], Point2D [x = 156.0, y = 36.0], Point2D [x = 156.0, y = 44.0], Point2D [x = 156.0, y = 52.0], Point2D [x = 156.0, y = 60.0], Point2D [x = 156.0, y = 68.0], Point2D [x = 156.0, y = 76.0], Point2D [x = 156.0, y = 84.0], Point2D [x = 156.0, y = 92.0], Point2D [x = 156.0, y = 100.0], Point2D [x = 156.0, y = 108.0], Point2D [x = 156.0, y = 116.0], Point2D [x = 156.0, y = 124.0], Point2D [x = 156.0, y = 132.0], Point2D [x = 156.0, y = 140.0], Point2D [x = 156.0, y = 148.0], Point2D [x = 156.0, y = 156.0], Point2D [x = 156.0, y = 164.0], Point2D [x = 156.0, y = 172.0], Point2D [x = 156.0, y = 180.0], Point2D [x = 156.0, y = 188.0], Point2D [x = 156.0, y = 196.0], Point2D [x = 156.0, y = 204.0], Point2D [x = 156.0, y = 212.0], Point2D [x = 156.0, y = 220.0], Point2D [x = 156.0, y = 228.0], Point2D [x = 156.0, y = 236.0], Point2D [x = 156.0, y = 244.0], Point2D [x = 156.0, y = 252.0], Point2D [x = 156.0, y = 260.0], Point2D [x = 156.0, y = 268.0], Point2D [x = 156.0, y = 276.0], Point2D [x = 156.0, y = 284.0], Point2D [x = 156.0, y = 292.0], Point2D [x = 156.0, y = 300.0], Point2D [x = 156.0, y = 308.0], Point2D [x = 156.0, y = 316.0], Point2D [x = 156.0, y = 324.0], Point2D [x = 156.0, y = 332.0], Point2D [x = 156.0, y = 340.0], Point2D [x = 156.0, y = 348.0], Point2D [x = 156.0, y = 356.0], Point2D [x = 156.0, y = 364.0], Point2D [x = 156.0, y = 372.0], Point2D [x = 156.0, y = 380.0], Point2D [x = 156.0, y = 388.0], Point2D [x = 156.0, y = 396.0], Point2D [x = 156.0, y = 404.0], Point2D [x = 156.0, y = 412.0], Point2D [x = 156.0, y = 420.0], Point2D [x = 156.0, y = 428.0], Point2D [x = 164.0, y = 4.0], Point2D [x = 164.0, y = 12.0], Point2D [x = 164.0, y = 20.0], Point2D [x = 164.0, y = 28.0], Point2D [x = 164.0, y = 36.0], Point2D [x = 164.0, y = 44.0], Point2D [x = 164.0, y = 52.0], Point2D [x = 164.0, y = 60.0], Point2D [x = 164.0, y = 68.0], Point2D [x = 164.0, y = 76.0], Point2D [x = 164.0, y = 84.0], Point2D [x = 164.0, y = 92.0], Point2D [x = 164.0, y = 100.0], Point2D [x = 164.0, y = 108.0], Point2D [x = 164.0, y = 116.0], Point2D [x = 164.0, y = 124.0], Point2D [x = 164.0, y = 132.0], Point2D [x = 164.0, y = 140.0], Point2D [x = 164.0, y = 148.0], Point2D [x = 164.0, y = 156.0], Point2D [x = 164.0, y = 164.0], Point2D [x = 164.0, y = 172.0], Point2D [x = 164.0, y = 180.0], Point2D [x = 164.0, y = 188.0], Point2D [x = 164.0, y = 196.0], Point2D [x = 164.0, y = 204.0], Point2D [x = 164.0, y = 212.0], Point2D [x = 164.0, y = 220.0], Point2D [x = 164.0, y = 228.0], Point2D [x = 164.0, y = 236.0], Point2D [x = 164.0, y = 244.0], Point2D [x = 164.0, y = 252.0], Point2D [x = 164.0, y = 260.0], Point2D [x = 164.0, y = 268.0], Point2D [x = 164.0, y = 276.0], Point2D [x = 164.0, y = 284.0], Point2D [x = 164.0, y = 292.0], Point2D [x = 164.0, y = 300.0], Point2D [x = 164.0, y = 308.0], Point2D [x = 164.0, y = 316.0], Point2D [x = 164.0, y = 324.0], Point2D [x = 164.0, y = 332.0], Point2D [x = 164.0, y = 340.0], Point2D [x = 164.0, y = 348.0], Point2D [x = 164.0, y = 356.0], Point2D [x = 164.0, y = 364.0], Point2D [x = 164.0, y = 372.0], Point2D [x = 164.0, y = 380.0], Point2D [x = 164.0, y = 388.0], Point2D [x = 164.0, y = 396.0], Point2D [x = 164.0, y = 404.0], Point2D [x = 164.0, y = 412.0], Point2D [x = 164.0, y = 420.0], Point2D [x = 164.0, y = 428.0], Point2D [x = 172.0, y = 4.0], Point2D [x = 172.0, y = 12.0], Point2D [x = 172.0, y = 20.0], Point2D [x = 172.0, y = 28.0], Point2D [x = 172.0, y = 36.0], Point2D [x = 172.0, y = 44.0], Point2D [x = 172.0, y = 52.0], Point2D [x = 172.0, y = 60.0], Point2D [x = 172.0, y = 68.0], Point2D [x = 172.0, y = 76.0], Point2D [x = 172.0, y = 84.0], Point2D [x = 172.0, y = 92.0], Point2D [x = 172.0, y = 100.0], Point2D [x = 172.0, y = 108.0], Point2D [x = 172.0, y = 116.0], Point2D [x = 172.0, y = 124.0], Point2D [x = 172.0, y = 132.0], Point2D [x = 172.0, y = 140.0], Point2D [x = 172.0, y = 148.0], Point2D [x = 172.0, y = 156.0], Point2D [x = 172.0, y = 164.0], Point2D [x = 172.0, y = 172.0], Point2D [x = 172.0, y = 180.0], Point2D [x = 172.0, y = 188.0], Point2D [x = 172.0, y = 196.0], Point2D [x = 172.0, y = 204.0], Point2D [x = 172.0, y = 212.0], Point2D [x = 172.0, y = 220.0], Point2D [x = 172.0, y = 228.0], Point2D [x = 172.0, y = 236.0], Point2D [x = 172.0, y = 244.0], Point2D [x = 172.0, y = 252.0], Point2D [x = 172.0, y = 260.0], Point2D [x = 172.0, y = 268.0], Point2D [x = 172.0, y = 276.0], Point2D [x = 172.0, y = 284.0], Point2D [x = 172.0, y = 292.0], Point2D [x = 172.0, y = 300.0], Point2D [x = 172.0, y = 308.0], Point2D [x = 172.0, y = 316.0], Point2D [x = 172.0, y = 324.0], Point2D [x = 172.0, y = 332.0], Point2D [x = 172.0, y = 340.0], Point2D [x = 172.0, y = 348.0], Point2D [x = 172.0, y = 356.0], Point2D [x = 172.0, y = 364.0], Point2D [x = 172.0, y = 372.0], Point2D [x = 172.0, y = 380.0], Point2D [x = 172.0, y = 388.0], Point2D [x = 172.0, y = 396.0], Point2D [x = 172.0, y = 404.0], Point2D [x = 172.0, y = 412.0], Point2D [x = 172.0, y = 420.0], Point2D [x = 172.0, y = 428.0], Point2D [x = 180.0, y = 4.0], Point2D [x = 180.0, y = 12.0], Point2D [x = 180.0, y = 20.0], Point2D [x = 180.0, y = 28.0], Point2D [x = 180.0, y = 36.0], Point2D [x = 180.0, y = 44.0], Point2D [x = 180.0, y = 52.0], Point2D [x = 180.0, y = 60.0], Point2D [x = 180.0, y = 68.0], Point2D [x = 180.0, y = 76.0], Point2D [x = 180.0, y = 84.0], Point2D [x = 180.0, y = 92.0], Point2D [x = 180.0, y = 100.0], Point2D [x = 180.0, y = 108.0], Point2D [x = 180.0, y = 116.0], Point2D [x = 180.0, y = 124.0], Point2D [x = 180.0, y = 132.0], Point2D [x = 180.0, y = 140.0], Point2D [x = 180.0, y = 148.0], Point2D [x = 180.0, y = 156.0], Point2D [x = 180.0, y = 164.0], Point2D [x = 180.0, y = 172.0], Point2D [x = 180.0, y = 180.0], Point2D [x = 180.0, y = 188.0], Point2D [x = 180.0, y = 196.0], Point2D [x = 180.0, y = 204.0], Point2D [x = 180.0, y = 212.0], Point2D [x = 180.0, y = 220.0], Point2D [x = 180.0, y = 228.0], Point2D [x = 180.0, y = 236.0], Point2D [x = 180.0, y = 244.0], Point2D [x = 180.0, y = 252.0], Point2D [x = 180.0, y = 260.0], Point2D [x = 180.0, y = 268.0], Point2D [x = 180.0, y = 276.0], Point2D [x = 180.0, y = 284.0], Point2D [x = 180.0, y = 292.0], Point2D [x = 180.0, y = 300.0], Point2D [x = 180.0, y = 308.0], Point2D [x = 180.0, y = 316.0], Point2D [x = 180.0, y = 324.0], Point2D [x = 180.0, y = 332.0], Point2D [x = 180.0, y = 340.0], Point2D [x = 180.0, y = 348.0], Point2D [x = 180.0, y = 356.0], Point2D [x = 180.0, y = 364.0], Point2D [x = 180.0, y = 372.0], Point2D [x = 180.0, y = 380.0], Point2D [x = 180.0, y = 388.0], Point2D [x = 180.0, y = 396.0], Point2D [x = 180.0, y = 404.0], Point2D [x = 180.0, y = 412.0], Point2D [x = 180.0, y = 420.0], Point2D [x = 180.0, y = 428.0], Point2D [x = 188.0, y = 4.0], Point2D [x = 188.0, y = 12.0], Point2D [x = 188.0, y = 20.0], Point2D [x = 188.0, y = 28.0], Point2D [x = 188.0, y = 36.0], Point2D [x = 188.0, y = 44.0], Point2D [x = 188.0, y = 52.0], Point2D [x = 188.0, y = 60.0], Point2D [x = 188.0, y = 68.0], Point2D [x = 188.0, y = 76.0], Point2D [x = 188.0, y = 84.0], Point2D [x = 188.0, y = 92.0], Point2D [x = 188.0, y = 100.0], Point2D [x = 188.0, y = 108.0], Point2D [x = 188.0, y = 116.0], Point2D [x = 188.0, y = 124.0], Point2D [x = 188.0, y = 132.0], Point2D [x = 188.0, y = 140.0], Point2D [x = 188.0, y = 148.0], Point2D [x = 188.0, y = 156.0], Point2D [x = 188.0, y = 164.0], Point2D [x = 188.0, y = 172.0], Point2D [x = 188.0, y = 180.0], Point2D [x = 188.0, y = 188.0], Point2D [x = 188.0, y = 196.0], Point2D [x = 188.0, y = 204.0], Point2D [x = 188.0, y = 212.0], Point2D [x = 188.0, y = 220.0], Point2D [x = 188.0, y = 228.0], Point2D [x = 188.0, y = 236.0], Point2D [x = 188.0, y = 244.0], Point2D [x = 188.0, y = 252.0], Point2D [x = 188.0, y = 260.0], Point2D [x = 188.0, y = 268.0], Point2D [x = 188.0, y = 276.0], Point2D [x = 188.0, y = 284.0], Point2D [x = 188.0, y = 292.0], Point2D [x = 188.0, y = 300.0], Point2D [x = 188.0, y = 308.0], Point2D [x = 188.0, y = 316.0], Point2D [x = 188.0, y = 324.0], Point2D [x = 188.0, y = 332.0], Point2D [x = 188.0, y = 340.0], Point2D [x = 188.0, y = 348.0], Point2D [x = 188.0, y = 356.0], Point2D [x = 188.0, y = 364.0], Point2D [x = 188.0, y = 372.0], Point2D [x = 188.0, y = 380.0], Point2D [x = 188.0, y = 388.0], Point2D [x = 188.0, y = 396.0], Point2D [x = 188.0, y = 404.0], Point2D [x = 188.0, y = 412.0], Point2D [x = 188.0, y = 420.0], Point2D [x = 188.0, y = 428.0]]
//        MOVE deMerge.java
//        COL 1.0,0.0,0.0,1.0 >>dot color

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
right now this class needs to be updated, no toggle pane with buttons - just the bare minimum --
plays the audio and carries out the information then loops and plays it again until the user
terminates the program
 */

public class Visualizer {

    public Pane rt;
    public Rectangle backdrop;
    private Timeline time;
    private String tsFilePath;
    private ArrayList<Point2D> timeStamps;
    private ArrayList<TSPlayer> playSequence;
    private int TSINDEX;
    private double currEnd;
    private double t;
    private ArrayList<String> endTSArray;

    /*
    plays the final programmed animation on the screen once with the audio in the background
     */

    public Visualizer(Pane root, String audioPath, ArrayList<String> timestamps){

        TSINDEX = 0;
        currEnd = 0;
        endTSArray = new ArrayList<>(); //end markers of each time stamp -- know the first one starts @ 0 -- start times are not needed
        for(int j = 0; j < timestamps.size(); j++){
            String ts = timestamps.get(j);
            String[] prse = ts.split(" ");
            String endTS = prse[2];
            endTSArray.add(endTS);
        }

        ///audioName ex: when I come around green day.wav
        String audiofileName = audioPath.split("/")[6];
        String audioRelativeFilePath = "/Users/isabellawhite/MSV_3.0/src/music/" + audiofileName;

        File f = new File(audioRelativeFilePath);
        Media med = new Media(f.toURI().toString());
        MediaPlayer play = new MediaPlayer(med);
        MediaView view = new MediaView(play);

        t = 0;
        playSequence = new ArrayList<>();
        timeStamps = new ArrayList<>();
        readTSFile(audioPath);
        rt = root;

        root.getChildren().add(view);
        play.play(); //plays the media and adds it to the pane

        tsFilePath = "";
        String[] parsePath = audioPath.split(Constants.FORWARD_SL);
        for(int i = 0; i < parsePath.length - 1; i++){
            tsFilePath = parsePath[i] + "/";
        }

        for(int y = 0; y < timeStamps.size(); y++){
            //there must be a text file
            int fileNum = y + 1;
            String fn = String.valueOf(fileNum);
            try {
                File myObj = new File(Constants.INITIAL_FP + "animation/" + tsFilePath + fn + Constants.TXT_TAG);
                BufferedReader br = new BufferedReader(new FileReader(myObj));
                String st = " ";

                //parameters for a tsplayer object
                Color circCol = Color.WHITE;
                double endTS = 0.00;
                Color bgCol = Color.WHITE;
                ArrayList<Point2D> coords = new ArrayList<>();
                String move = "";
                int count = 0;

                while ((st = br.readLine()) != null) {
                    count++;
                    if(count > 1){
                        if(st.length() == 1){
                            count = 0;
                            //new TS player - store old variables in a TSPlayer object before they get reset
                            TSPlayer ts = new TSPlayer(endTS, circCol, bgCol, coords, move);
                            playSequence.add(ts);
                        } else {
                            switch(count){
                                case 2:
                                    Color col = parseCol(st);
                                    bgCol = col;
                                    break;
                                case 3:
                                    //group label: GROUP 1
                                    String[] parse = st.split(" ");
                                    int index = Integer.valueOf(parse[1]);
                                    index = index - 1;
                                    String endingTime = endTSArray.get(index);
                                    endTS = Double.valueOf(endingTime);
                                    break;
                                case 4:
                                    //coords: COORDS [Point2D [x = 4.0, y = 4.0],
                                    String[] elements = st.split(" ");
                                    String xCoord = null;
                                    String yCoord = null;
                                    for(int g = 0; g < elements.length; g++){
                                        String dummy = elements[g];
                                        if (dummy.equals("[x")) {
                                            xCoord = elements[g+2];
                                        } else if(dummy.equals("y")){
                                            yCoord = elements[g+2];
                                        }

                                        if(xCoord != null && yCoord != null){
                                            String x = xCoord.split(",")[0];
                                            double ex = Double.valueOf(x);
                                            String valY = yCoord.split("]")[0];
                                            double why = Double.valueOf(valY);
                                            Point2D p = new Point2D(ex, why);
                                            coords.add(p);
                                            xCoord = null;
                                            yCoord = null;
                                        }

                                    }
                                    break;
                                case 5:
                                    //move ex: MOVE deMerge.java
                                    String[] parseString = st.split(" ");
                                    String movement = parseString[1];
                                    move = movement;
                                    break;
                                default:
                                    // dot color: COL 1.0,0.0,0.0,1.0
                                    String[] p = st.split(" ");
                                    String r = p[1];
                                    Color dotCol = parseCol(r);
                                    circCol = dotCol;
                                    break;
                            }
                        }
                    }
                    TSPlayer ts = new TSPlayer(endTS, circCol, bgCol, coords, move);
                    playSequence.add(ts);
                }

            } catch (FileNotFoundException e) {
                System.out.println("file not found error in Visualizer : ");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        backdrop = new Rectangle(Constants.APP_WIDTH - 50, Constants.APP_HEIGHT, Color.BLACK);
        rt.getChildren().add(backdrop);

        setUpTimeline();

    }

    public Rectangle getNewBG(Color c){
        return new Rectangle(Constants.APP_WIDTH - 50, Constants.APP_HEIGHT, c);
    }

    public Color parseCol(String line){
        String[] rbg = line.split(Constants.COMMA);
        float[] RBG = new float[4];
        for(int i = 0; i < 4; i++){
            RBG[i] = (Float.parseFloat(rbg[i]));
        }
        Color backdrop = new Color(RBG[0], RBG[1], RBG[2], RBG[3]);
        return backdrop;
    }

    public void readTSFile(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] parseData = data.split(Constants.FORWARD_SL);
                String ts = parseData[0];
                String[] coords = ts.split(" ");
                double x = Double.valueOf(coords[0]);
                double y = Double.valueOf(coords[2]);
                timeStamps.add(new Point2D(x, y));
                //EX DATA: 1.0 . 252.47 / LL31.1.jpg
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found error in Visualizer : ");
            e.printStackTrace();
        }
    }

    public void mover(String move, ArrayList<Circle> dots, Color dotCol,Pane p){
        switch (move) {
            case "bigInOut.java":
                bigInOut b = new bigInOut(dots, 1, dotCol, 1, p);
                break;
            case "bounce.java":
                bounce bc = new bounce(dots, 1, dotCol, p);
                break;
            case "deMerge.java":
                deMerge d = new deMerge(dots, 1, dotCol, p);
                break;
            case "merge.java":
                //TODO: merge not working - fix - needs a borderpane as an input - p is not one
//                        merge m = new merge(dots, 1, dotCol, p);
                break;
            case "opacityFlicker.java":
                opacityFlicker op = new opacityFlicker(dots, 1, dotCol, p);
                break;
            case "pixBlink.java":
                pixBlink px = new pixBlink(dots, 1, dotCol, p);
                break;
            case "Rotate.java":
                Rotate r = new Rotate(1, dots, 1, dotCol, new Point2D(200, 200), p);
                break;
            default: //translate.java
                translate t = new translate(dots, 1, 1, 2, dotCol, p);
                break;
        }
    }

    public void update(){

        t = t + 0.1; //keeps time in seconds code is updated with a keyframe of 0.1 seconds

        TSPlayer currPlayer = playSequence.get(TSINDEX);
        String move = currPlayer.getMove();

        if(t > playSequence.get(TSINDEX).getEnd()) {
           TSINDEX++;
           if(TSINDEX == playSequence.size()){
               time.stop();
           }
           Pane p = new Pane();
           p.getChildren().add(getNewBG(currPlayer.getBG()));

           rt.getChildren().add(p);
           mover(move, currPlayer.getDs(), currPlayer.getDotCol(), p);
        }

    }

    public void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KF_DUR), (ActionEvent e) -> update());
        this.time = new Timeline(kf);
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
