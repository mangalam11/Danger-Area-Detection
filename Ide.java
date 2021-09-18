package com.example.dsaproject;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Set;
import java.lang.Math;
import java.util.HashSet;
import static com.example.dsaproject.JarvisMethod.*;
class ArrayType{
 public int x,y;
 public ArrayType(int x,int y){
 this.x=x;
 this.y=y;
 }
}
class FindingPoints {
 public static int intensityExceptable;
 private int maxIntensity;
 private int radius;
 public static ArrayList<ArrayType>arrayList = new ArrayList<ArrayType>();
 private int xLocationOfCenter,yLocationOfCenter;
 public FindingPoints(int maxIntensity,int x,int y){
 this.maxIntensity=maxIntensity;
 radius = 1;
 xLocationOfCenter=x;
 yLocationOfCenter=y;
 setRadius();
 }
 public void setRadius(){
 while(maxIntensity/(radius*radius)>intensityExceptable) {
 radius++;
 }
 evaluatePoints();
 }
 public void evaluatePoints() {
 System.out.println("radius: "+radius);
 for(int i=0;i<=330;i=i+30) {
 double theta = Math.PI*i/180;
 int a=((int)(xLocationOfCenter + radius * Math.cos(theta)));
 int b=((int)(yLocationOfCenter + radius * Math.sin(theta)));
 ArrayType q = new ArrayType(a,b);
 arrayList.add(q);
 }
 }
 public static void pushPointsToTheArray(ArrayType q){
 arrayList.add(q);
 }
}
class JarvisMethod{
 public static Vector<ArrayType> hull = new Vector<ArrayType>();
 public static int timeTaken;
 public static int orientation(ArrayType p, ArrayType q, ArrayType r)
 {
 int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
 if (val == 0)
 return 0;
 return (val > 0)? 1: 2;
 }
 public static void convexHull(ArrayList<ArrayType> points, int n)
 {
 long start = System.currentTimeMillis();
 if (n < 3)
 return;
 int l = 0;
 for (int i = 1; i < n; i++)
 if (points.get(i).x < points.get(l).x)
 l = i;
 int p = l, q;
 do
 {
 hull.add(points.get(p));
 q = (p + 1) % n;
 for (int i = 0; i < n; i++)
 {
 if (orientation(points.get(p), points.get(i), points.get(q)) == 2)
 q = i;
 }
 p = q;
 } while (p != l);
 long end = System.currentTimeMillis();
 timeTaken= (int)(end-start);
 }
}
class QuickHull{
 public static int timeTaken;
 //HashSet is used to remove duplicate elements from the set
 public static Set<ArrayType> hull = new HashSet<ArrayType>();
 public int findSide(ArrayType p1, ArrayType p2, ArrayType p)
 {
 int val = (p.y-p1.y)*(p2.x-p1.x)-(p2.y-p1.y)*(p.x-p1.x);
 if (val > 0)
 return 1;
 if (val < 0)
 return -1;
 return 0;
 }
 int lineDist(ArrayType p1,ArrayType p2,ArrayType p)
 {
 return Math.abs((p.y-p1.y)*(p2.x-p1.x)-(p2.y - p1.y) * (p.x - p1.x));
 }
 void quickHull(ArrayList<ArrayType>a,int n,ArrayType p1,ArrayType p2,int side)
 {
 int ind = -1;
 int max_dist = 0;
 for (int i=0; i<n; i++)
 {
 int temp = lineDist(p1, p2, a.get(i));
 if (findSide(p1, p2, a.get(i)) == side && temp > max_dist)
 {
 ind = i;
 max_dist = temp;
 }
 }
 if (ind == -1)
 {
 hull.add(p1);
 hull.add(p2);
 return;
 }
 quickHull(a, n, a.get(ind), p1, -findSide(a.get(ind), p1, p2));
 quickHull(a, n, a.get(ind), p2, -findSide(a.get(ind), p2, p1));
 }
 void printHull(ArrayList<ArrayType> a, int n)
 {
 long start= System.currentTimeMillis();
 if (n < 3)
 {
 System.out.println("Convex hull not possible\n");
 return;
 }
 int min_x = 0, max_x = 0;
 for (int i=1; i<n; i++)
 {
 if (a.get(i).x < a.get(min_x).x)
 min_x = i;
 if (a.get(i).x > a.get(max_x).x)
 max_x = i;
 }
 quickHull(a, n, a.get(min_x), a.get(max_x), 1);
 quickHull(a, n, a.get(min_x), a.get(max_x),-1);
 long end= System.currentTimeMillis();
 timeTaken = (int)(end-start);
 }
}
public class MainActivity extends AppCompatActivity {
 int n = 0, taskCompleted = 0, valueTaken = 0, image = 0;
 Button buttons[] = new Button[4];
 public void startActual() {
 ImageView questionPhoto = (ImageView) findViewById(R.id.imageView6);
 questionPhoto.setVisibility(View.VISIBLE);
 TextView questionStatement = (TextView) findViewById(R.id.textView6);
 String a = "Question Statement:-\n\tImagine a modern city with censors positioned uniformly
all over. When a disaster such a leak or a nuclear ";
 a += "radiation leak.Determine the boundary region till where we need to evacuate , so that we
are safe ??";
 questionStatement.setVisibility(View.VISIBLE);
 questionStatement.setText(a);
 Button b1 = (Button) findViewById(R.id.button);
 b1.setVisibility(View.VISIBLE);
 TextView c = (TextView) findViewById(R.id.textView8);
 c.setVisibility(View.VISIBLE);
 }
 public void procedded(View view) {
 ImageView questionPhoto = (ImageView) findViewById(R.id.imageView6);
 questionPhoto.setVisibility(View.INVISIBLE);
 TextView questionStatement = (TextView) findViewById(R.id.textView6);
 questionStatement.setVisibility(View.INVISIBLE);
 TextView c = (TextView) findViewById(R.id.textView8);
 c.setVisibility(View.INVISIBLE);
 c = (TextView) findViewById(R.id.textView9);
 c.setText("Enter the total number of NUCLEAR LEAKS");
 Button b1 = (Button) findViewById(R.id.button);
 b1.setVisibility(View.INVISIBLE);
 EditText d = (EditText) findViewById(R.id.editText);
 d.setVisibility(View.VISIBLE);
 Button b = (Button) findViewById(R.id.button2);
 b.setVisibility(View.VISIBLE);
 TextView q = (TextView) findViewById(R.id.textView10);
 q.setText("Enter the maximum value that max value(Intensity) the people can tolerate");
 b.setText("Submit");
 d = (EditText) findViewById(R.id.editText2);
 d.setVisibility(View.VISIBLE);
 }
 public void mainWork(View view) {
 EditText d = (EditText) findViewById(R.id.editText);
 EditText e = (EditText) findViewById(R.id.editText2);
 EditText f = (EditText) findViewById(R.id.editText3);
 TextView a = (TextView) findViewById(R.id.textView9);
 TextView b = (TextView) findViewById(R.id.textView10);
 TextView c = (TextView) findViewById(R.id.textView11);
 if (taskCompleted == 0) {
 n = Integer.parseInt(d.getText().toString());
 FindingPoints.intensityExceptable = Integer.parseInt(e.getText().toString());
 a.setText("Enter the maximum Intensity emitted out by this nuclear power plant :-");
 b.setText("Enter the x coordinate of the location of nuclear center");
 c.setText("Enter the y coordinate of the location of nuclear center");
 f.setVisibility(View.VISIBLE);
 d.setText("");
 e.setText("");
 f.setText("");
 taskCompleted++;
 } else if (taskCompleted == 1 && valueTaken < n) {
 int maxI = Integer.parseInt(d.getText().toString());
 int x = Integer.parseInt(e.getText().toString());
 int y = Integer.parseInt(f.getText().toString());
 FindingPoints g = new FindingPoints(maxI, x, y);
 for (ArrayType i : FindingPoints.arrayList)
 Log.i(Integer.toString(i.x), Integer.toString(i.y));
 Log.i("leaveing lines", "");
 Log.i("leaveing lines", "");
 d.setText("");
 e.setText("");
 f.setText("");
 if (valueTaken == n - 1) {
 a.setText("CHOOSE ANY ONE");
 b.setVisibility(View.INVISIBLE);
 c.setVisibility(View.INVISIBLE);
 d.setVisibility(View.INVISIBLE);
 e.setVisibility(View.INVISIBLE);
 f.setVisibility(View.INVISIBLE);
 view.setVisibility(View.INVISIBLE);
 for (int i = 0; i < 4; i++) {
 buttons[i].setVisibility(View.VISIBLE);
 buttons[i].setAlpha(0f);
 buttons[i].animate().alphaBy(1f).setDuration(1500);
 }
 }
 valueTaken++;
 }
 }
 public void finalTask(View view) {
 Button b = (Button) view;
 int tag = Integer.parseInt(b.getTag().toString());
 if (tag == 0) {
 convexHull(FindingPoints.arrayList, FindingPoints.arrayList.size());
 b.animate().alpha(0f).setDuration(1000);
 b.setVisibility(View.INVISIBLE);
 TextView a = (TextView) findViewById(R.id.textView15);
 a.setText("Jarvis algorithm is \nsuccessfully run");
 } else if (tag == 1) {
 QuickHull c = new QuickHull();
 c.printHull(FindingPoints.arrayList, FindingPoints.arrayList.size());
 b.animate().alpha(0f).setDuration(1000);
 b.setVisibility(View.INVISIBLE);
 TextView a = (TextView) findViewById(R.id.textView14);
 a.setText("QuickHull algorithm is \nsuccessfully run");
 } else if (tag == 2) {
 b.animate().alpha(0f).setDuration(1000);
 b.setVisibility(View.INVISIBLE);
 TextView a = (TextView) findViewById(R.id.textView13);
 String c = "Jarvis: " + Integer.toString(QuickHull.timeTaken) + "\nQuickHull: " +
Integer.toString(JarvisMethod.timeTaken);
 a.setText(c);
 } else {
 TextView a1 = (TextView) findViewById(R.id.textView9);
 a1.setVisibility(View.INVISIBLE);
 a1 = (TextView) findViewById(R.id.textView15);
 a1.setVisibility(View.INVISIBLE);
 a1 = (TextView) findViewById(R.id.textView14);
 a1.setVisibility(View.INVISIBLE);
 a1 = (TextView) findViewById(R.id.textView13);
 a1.setVisibility(View.INVISIBLE);
 view.setVisibility(View.INVISIBLE);
 final GraphView graphView = (GraphView) findViewById(R.id.scattergrid);
 graphView.setVisibility(View.VISIBLE);
 PointsGraphSeries<DataPoint> xySeries = new PointsGraphSeries<>();
 final ArrayList<ArrayType> a = new ArrayList<ArrayType>();
 for (ArrayType i : QuickHull.hull) {
 a.add(i);
 }
 bubbleSort(a);
 bubbleSort(FindingPoints.arrayList);
 for (ArrayType i : FindingPoints.arrayList)
 Log.i(Integer.toString(i.x), Integer.toString(i.y));
 for (ArrayType i : FindingPoints.arrayList) {
 int x = i.x;
 int y = i.y;
 xySeries.appendData(new DataPoint(x, y), true, 1000);
 }
 xySeries.setShape(PointsGraphSeries.Shape.POINT);
 xySeries.setColor(Color.BLUE);
 xySeries.setSize(5f);
 graphView.getViewport().setScalable(true);
 graphView.getViewport().setScalableY(true);
 graphView.getViewport().setScrollable(true);
 graphView.getViewport().setScrollableY(true);
 graphView.getViewport().setYAxisBoundsManual(true);
 graphView.getViewport().setMaxY(40);
 graphView.getViewport().setMinY(-40);
 graphView.getViewport().setXAxisBoundsManual(true);
 graphView.getViewport().setMaxX(40);
 graphView.getViewport().setMinX(-40);
 graphView.addSeries(xySeries);
 final Button b1 = (Button) findViewById(R.id.button);
 b1.setVisibility(View.VISIBLE);
 b1.setText("Proceed to the final Convex Hull graph");
 b1.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 b1.setText("Visit to the last section");
 graphView.removeAllSeries();
 PointsGraphSeries<DataPoint> xySeries = new PointsGraphSeries<>();
 for(ArrayType i:a){
 int x = i.x;
 int y = i.y;
 xySeries.appendData(new DataPoint(x,y),true,1000);
 }
 xySeries.setShape(PointsGraphSeries.Shape.POINT);
 xySeries.setColor(Color.RED);
 xySeries.setSize(5f);
 graphView.getViewport().setYAxisBoundsManual(true);
 graphView.getViewport().setMaxY(40);
 graphView.getViewport().setMinY(-40);
 graphView.getViewport().setXAxisBoundsManual(true);
 graphView.getViewport().setMaxX(40);
 graphView.getViewport().setMinX(-40);
 graphView.addSeries(xySeries);
 b1.setOnClickListener(new View.OnClickListener(){
 public void onClick(View view){
 view.setVisibility(View.INVISIBLE);
 graphView.removeAllSeries();
 lastAndFinal();
 }
 });
 }
 });
 }
 }
 public void lastAndFinal(){
 double x=0,y1,y2;
 GraphView graphView = findViewById(R.id.scattergrid);
 LineGraphSeries<DataPoint> series1,series2;
 series1 = new LineGraphSeries<>();
 series2 = new LineGraphSeries<>();
 int numDataPoints = 4000;
 for(int i=0;i<numDataPoints;i++){
 x+=0.1;
 y1=x*x;
 y2=x*Math.log(x)/Math.log(2);
 series1.appendData(new DataPoint(x,y1),true,4000);
 series2.appendData(new DataPoint(x,y2),true,4000);
 }
 graphView.addSeries(series1);
 graphView.addSeries(series2);
 series1.setColor(Color.RED);
 series2.setColor(Color.BLUE);
 graphView.getLegendRenderer().setVisible(true);
 graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
 graphView.setTitle("TIME COMPLEXITY");
 graphView.setTitleTextSize(60);
 graphView.setTitleColor(Color.GREEN);
 series1.setTitle("JARVIS ALGORITHM O(n^2)");
 series2.setTitle("QUICKHULL ALGORITHM O(n*log(n))");
 GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
 gridLabel.setHorizontalAxisTitle("AS N GROWS:-");
 gridLabel.setVerticalAxisTitle("TIME TAKEN:-");
 gridLabel.setTextSize(20);
 graphView.getViewport().setYAxisBoundsManual(true);
 graphView.getViewport().setMaxY(20000);
 graphView.getViewport().setMinY(0);
 graphView.getViewport().setXAxisBoundsManual(true);
 graphView.getViewport().setMaxX(350);
 graphView.getViewport().setMinX(0);
 graphView.setOnClickListener(new View.OnClickListener(){
 public void onClick(View view){
 Intent intent = new Intent(MainActivity.this,secondPage.class);
 startActivity(intent);
 }
 });
 }
 void bubbleSort(ArrayList<ArrayType> arr) {
 int n = arr.size();
 for (int i = 0; i < n - 1; i++)
 for (int j = 0; j < n - i - 1; j++)
 if (arr.get(j).x > arr.get(j + 1).x) {
 // swap temp and arr[i]
 ArrayType temp = arr.get(j);
 arr.set(j, arr.get(j + 1));
 arr.set(j + 1, temp);
 }
 }
 public void imageClicked(View view) {
 view.setVisibility(View.INVISIBLE);
 buttons[0] = (Button) findViewById(R.id.button3);
 buttons[1] = (Button) findViewById(R.id.button4);
 buttons[2] = (Button) findViewById(R.id.button5);
 buttons[3] = (Button) findViewById(R.id.button6);
 final ImageView arr[] = new ImageView[5];
 final TextView text[] = new TextView[5];
 arr[0] = (ImageView) findViewById(R.id.imageView1);
 arr[1] = (ImageView) findViewById(R.id.imageView2);
 arr[2] = (ImageView) findViewById(R.id.imageView3);
 arr[3] = (ImageView) findViewById(R.id.imageView4);
 arr[4] = (ImageView) findViewById(R.id.imageView5);
 text[0] = (TextView) findViewById(R.id.textView1);
 text[1] = (TextView) findViewById(R.id.textView2);
 text[2] = (TextView) findViewById(R.id.textView3);
 text[3] = (TextView) findViewById(R.id.textView4);
 text[4] = (TextView) findViewById(R.id.textView5);
 for (int i = 0; i < 5; i++) {
 arr[i].setVisibility(View.VISIBLE);
 arr[i].setTranslationX(-1000f);
 arr[i].setTranslationY(-1000f);

arr[i].animate().translationXBy(1000f).translationYBy(1000f).rotationBy(1080).setDuration(2000);
 text[i].setVisibility(View.VISIBLE);
 text[i].setTranslationX(1000f);
 text[i].setTranslationY(1000f);
 text[i].animate().translationXBy(-1000f).translationYBy(-
1000f).rotationBy(1080).setDuration(2000);
 }
 new CountDownTimer(3000, 1000) {
 public void onTick(long milliSecond) {
 }
 public void onFinish() {
 for (int i = 0; i < 5; i++) {
 arr[i].animate().alpha(0f).setDuration(2000);
 text[i].animate().alpha(0f).setDuration(2000);
 }
 }
 }.start();
 new CountDownTimer(4500, 1000) {
 public void onTick(long milliSecond) {
 }
 public void onFinish() {
 startActual();
 }
 }.start();
 }
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 }
}