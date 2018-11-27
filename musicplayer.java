package musicplayer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.Encoder;
import java.io.File;
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Timer;

import java.util.TimerTask;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
//import DemoScrollBarUI

class music//歌曲信息类
{
	String name;
	int time;
	String song_name;
	int minute;
	int second;
	int status;
	music(String na,int ti,String song_name,int minute,int second,int status)
	{
		this.name=na;
		this.time=ti;
		this.song_name=song_name;
		this.minute=minute;
		this.second=second;
		this.status=status;
	}
}


class lirics//歌词类
{
	int minute;
	int second;
	String line; 
	int time;
	lirics(int minute,int second,String line,int time)
	{
		this.minute=minute;
		this.second=second;
		this.line=line;
		this.time=time;
	}
}

class audioplay{//播放音乐类
	   AudioClip adc;// 声音音频剪辑对象
	   URL url;
	   boolean adcFlag=false;
	   boolean playFlag=false;
	   void SetPlayAudioPath(String path){
	      try{  
	           url = new URL(path);  
	          // System.out.println(adc.toString());
	           if(adcFlag==true){adc.stop();playFlag=false;}
	           adc = Applet.newAudioClip(url);
	           adcFlag=true;
	         }
	      catch (MalformedURLException e1) {
	              e1.printStackTrace();  
	         }  
	   }
	   void play(){     //开始播放
	           adc.play();
	           playFlag=true;
	   }   
	   void stop(){     //停止播放
	           adc.stop();   
	           playFlag=false;
	   }
	}

class MyExtendsJFrame extends JFrame implements ActionListener,MouseListener
{ //窗口类
	JLabel background;//背景控件
	JLabel underground;//下部
	JLabel upground;//上部
	JLabel tubiao;//播放器图标
	JButton buttonPlay;//播放 按钮
	JButton buttonStop;//停止播放
	JButton buttonPlay1;//下一首 按钮
	JButton buttonPlay2;//上一首 按钮
	JButton buttonPlay3;//打开 按钮
	JButton buttonPlay4;// 循环 按钮
	JButton buttonList;//播放列表显示/隐藏 按钮
	JTextPane textLyrics;//主体歌词控件
    JTextArea textLyrics2;//下端歌词控件
    JTextArea music_info;//歌曲信息（歌手名+专辑名）
	JLabel playTime;//播放进度条控件
	JList listPlayFile;//播放列表控件
	Timer nTimer;//定时器对象
	JTextArea textarea;//歌名控件
	JTextArea textarea2;//歌名控件2
	JTextArea runtime;//当前歌曲时间
	JTextArea songtime;//歌曲总时间
	JLabel gif;//动图
	JLabel album_pic;//专辑图片
	audioplay audioPlay;
	LinkedList<music>mylist;//播放列表
	Vector<String> vt1=new Vector ();//创建范围Vector对象，用于点击播放列表的索引操作
	JButton closeBtn = null;
	JButton maxBtn = null;
	JButton minBtn = null; 
	JButton change_color;
	int xOld = 0;
	int yOld = 0;
	public MyExtendsJFrame()
	{
		audioPlay=new audioplay();  //创建播放对象
		mylist=new LinkedList<music>();	
		setTitle("播放器");//软件名
		setBounds(160,100,800,500);	//设置窗口大小		
		setLayout(null);//空布局	
		init();   //添加控件的操作封装成一个函数         
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	void init()
	{//添加的控件
		//设置背景图片
		Icon img=new ImageIcon(".//timg1.jpg");     //创建图标对象			
		background = new JLabel(img);//设置背景图片
		background.setBounds(0,0,800,500);//设置背景控件大小
	    getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
		((JPanel)getContentPane()).setOpaque(false); //控件透明
		//设置上边框		
		Icon imgu=new ImageIcon(".//上部.png");     
		upground = new JLabel(imgu);
		upground.setBounds(0,0,800,41);
	    getLayeredPane().add(upground, new Integer(Integer.MIN_VALUE+1));
		((JPanel)getContentPane()).setOpaque(false); 
		//设置下边框
		Icon img_d=new ImageIcon(".//下旁.png");    	
		underground = new JLabel(img_d);
		underground.setBounds(0,500-41,800,41);
	    getLayeredPane().add(underground, new Integer(Integer.MIN_VALUE+1));
		((JPanel)getContentPane()).setOpaque(false); 
		//加入自设图标
		Icon img_t=new ImageIcon(".//图标.jpg");    	
		tubiao = new JLabel(img_t);
		tubiao.setBounds(0,0,183,41);
	    getLayeredPane().add(tubiao, new Integer(Integer.MIN_VALUE+2));
		((JPanel)getContentPane()).setOpaque(false); 		
		//设置动态图
		Icon img1=new ImageIcon(".//静止唱片机.png");     	
		gif = new JLabel(img1);
		gif.setBounds(380,112,400,270);
		add(gif);	      
		//设置当前歌曲播放时间
	    runtime=new JTextArea("00 : 00");
	    runtime.setBounds(500,500-38,40,25);
	    runtime.setForeground(Color.MAGENTA);//歌词控件字体颜色
	    runtime.setOpaque(false);
	    add(runtime);	
	    /*
	     * 设置专辑图片
	     * 失败。在gif之下
	    Icon img_album=new ImageIcon(".//Vk-山外小楼夜听雨.png");     	
		album_pic = new JLabel(img_album);
		album_pic.setBounds(380,112,150,270);
		getLayeredPane().add(album_pic, new Integer(Integer.MIN_VALUE+2));
		add(album_pic);	
	    */
	    //设置歌曲总时间
	    songtime=new JTextArea("/00 : 00");
	    songtime.setBounds(540,500-38,50,25);
	    songtime.setForeground(Color.MAGENTA);//歌词控件字体颜色
	    songtime.setOpaque(false);
	    add(songtime);	

	    //设置播放按钮
		buttonPlay=new JButton();//添加     播放   按钮
	    buttonPlay.setBounds(45,500-40,38,38); //设置播放按钮大小
	    Icon icon=new ImageIcon(".//start.png");//创建播放图标对象
	    buttonPlay.setIcon(icon);	      //设置播放图标
	    buttonPlay.setBorderPainted(false);
	    buttonPlay.addActionListener(this);//添加单机关联事件
	    add(buttonPlay);//添加播放按钮至窗口中	    
	    //设置停止按钮
	    buttonStop=new JButton();
	    buttonStop.setBounds(45,500-40,38,38);
	    Icon iconstop=new ImageIcon(".//stop.png");
	    buttonStop.setIcon(iconstop);	     
	    buttonStop.setBorderPainted(false);
	    buttonStop.addActionListener(this);
	    add(buttonStop);	      
	    //设置下一首按钮
	    buttonPlay1=new JButton();//添加    下一首    按钮
	    buttonPlay1.setBounds(90,500-38,35,35); 
	    Icon icon1=new ImageIcon(".//next.png");
	    buttonPlay1.setIcon(icon1);	     
	    buttonPlay1.setBorderPainted(false);
	    buttonPlay1.addActionListener(this);
	    add(buttonPlay1);      
	    //设置上一首按钮
	    buttonPlay2=new JButton();
	    buttonPlay2.setBounds(3,500-38,35,35);
	    Icon icon2=new ImageIcon(".//last.png");
	    buttonPlay2.setIcon(icon2);	      
	    buttonPlay2.setBorderPainted(false);
	    buttonPlay2.addActionListener(this);
	    add(buttonPlay2);      
	    //设置打开文件按钮
	    buttonPlay3=new JButton();
	    buttonPlay3.setBounds(800-36-40-40,500-33-4,36,33); 
	    Icon icon3=new ImageIcon(".//open.png");
	    buttonPlay3.setIcon(icon3);	      
	    buttonPlay3.setBorderPainted(false);
	    buttonPlay3.addActionListener(this);
	    add(buttonPlay3);   
	    //设置循环按钮
	    buttonPlay4=new JButton();
	    buttonPlay4.setBounds(140,500-33-4,36,33); 
	    Icon icon4=new ImageIcon(".//loop.png");
	    buttonPlay4.setIcon(icon4);	      
	    buttonPlay4.setBorderPainted(false);
	    buttonPlay4.addActionListener(this);
	    add(buttonPlay4);
	    //设置播放列表按钮（显示与消失）    
	    buttonList=new JButton();
	    buttonList.setBounds(800-36-35,500-33-2,36,33); 
	    Icon icon_list=new ImageIcon(".//list.png");
	    buttonList.setIcon(icon_list);	      
	    buttonList.setBorderPainted(false);
	    buttonList.addActionListener(this);
	    add(buttonList);
	    //下端显示歌曲名
	    textarea= new JTextArea(" ");
	    textarea.setBounds(200,500-38,450,25);
	    textarea.setForeground(Color.BLACK);//歌词控件字体颜色
	    getLayeredPane().add(textarea, new Integer(Integer.MIN_VALUE+3));
	    textarea.setOpaque(false);
	    add(textarea);
	    //设置播放列表
	    listPlayFile=new JList();	  //创建播放列表 
	    listPlayFile.setBounds(100,120,250,380); //设置播放列表大小 
	    listPlayFile.setOpaque(true);//设置播放列表透明
	    listPlayFile.setBackground(new Color(0, 0, 0, 0));//设置播放列表背景颜色
	    listPlayFile.setForeground(Color.white);//设置播放列表字体颜色
	    add(listPlayFile);       //添加播放列表至窗口中
	    listPlayFile.addMouseListener(this);//添加播放列表的双击事件关联
	    listPlayFile.setVisible(false); 
	    listPlayFile.setVisible(false); 
	    //设置歌名显示（主页面）
	    textarea2= new JTextArea(" ");
	    textarea2.setBounds(100,50,250,25);
	    textarea2.setForeground(Color.CYAN);
	    textarea2.setFont(new Font("黑体",Font.BOLD,20));
	    getLayeredPane().add(textarea2, new Integer(Integer.MIN_VALUE+3));
	    textarea2.setOpaque(false);
	    add(textarea2);	  
	    //设置歌曲信息显示
	    music_info = new JTextArea(" ");
	    music_info.setBounds(100,75,250,35);
	    music_info.setForeground(Color.green);
	    music_info.setFont(new Font("楷体",Font.BOLD,12));
	    getLayeredPane().add(music_info, new Integer(Integer.MIN_VALUE+3));
	    music_info.setOpaque(false);
	    add(textarea2);	  
	    //主界面歌词显示
	    textLyrics=new JTextPane();   //创建歌词控件    
	    //JScrollPane scrollPane = new JScrollPane(textLyrics);
	    //scrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
	    textLyrics.setBounds(100,120,250,315);//设置歌词控件大小
	    textLyrics.setForeground(Color.LIGHT_GRAY);//歌词控件字体颜色
	    textLyrics.setFont(new Font("隶书",Font.BOLD,15));
	    textLyrics.setOpaque(false);//歌词控件透明
	    add(textLyrics);    //添加歌词控件至窗口中
	    //add(scrollPane);
	    textLyrics.setText("\n\n\n\n\n"+"当前还没有歌曲播放呢(￣▽￣)\n"+
             	 		"赶紧点击右下角的\"电脑\"图标\n"+"选取您想欣赏的歌曲吧 ~\n"
	    				+"小Z在这里等你哦O(∩_∩)O\n");//歌词控件添加文字
	    	    
	    //设置下端歌词（逐行显示）
	    textLyrics2=new JTextArea(" ");   //创建歌词控件    
	    textLyrics2.setBounds(300,500-17,250,15);//设置歌词控件大小
	    textLyrics2.setForeground(Color.BLUE);//歌词控件字体颜色
	    textLyrics2.setFont(new Font("楷体",Font.BOLD,15));
	    textLyrics2.setOpaque(false);//歌词控件透明
	    add(textLyrics2);    //添加歌词控件至窗口中
	    //add(scrollPane);
	    textLyrics2.setText("欢迎使用Zies播放器！");//歌词控件添加文字	      
	    //设置进度条
	    Icon img2=new ImageIcon(".//jindu.png");     //创建图标对象
	    playTime = new JLabel(img2);	  		//创建播放进度条对象
	    playTime.setBounds(0,500-43,0,3);	  	//设置播放进度条对象大小	      
	    add(playTime); //添加播放进度条至窗口中
	    //设置换肤按钮
	    change_color = new JButton("");
	    Icon icon_color=new ImageIcon(".//皮肤1.jpg");
	    change_color.setIcon(icon_color);	      
	    change_color.setBounds(685, 10, 24, 23);
	    change_color.setBorderPainted(false);
	    change_color.setHorizontalAlignment(JButton.CENTER);
	    change_color.addActionListener(this);
	    add(change_color);
	    
	    //设置关闭按钮
	    closeBtn = new JButton("");
	    Icon icon5=new ImageIcon(".//close.png");
	    closeBtn.setIcon(icon5);	      
	    closeBtn.setBounds(775, 8, 24, 24);
	    closeBtn.setBorderPainted(false);
	    closeBtn.setHorizontalAlignment(JButton.CENTER);
	    closeBtn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            //dispose();
	            System.exit(0);
	            //使用dispose();也可以关闭只是不是真正的关闭
	        }
	    });
	    this.add(closeBtn);
	    //设置最大化按钮
	    maxBtn = new JButton("");
	    Icon icon6=new ImageIcon(".//max.png");
	    maxBtn.setIcon(icon6);	      
	    maxBtn.setBounds(745, 8, 24, 24);
	    maxBtn.setBorderPainted(false);
        maxBtn.setHorizontalAlignment(JButton.CENTER);
        maxBtn.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                setExtendedState(JFrame.MAXIMIZED_BOTH);//最大化窗体
               
            }
        });
        this.add(maxBtn);
        //设置最小化按钮
        minBtn = new JButton("");
        Icon icon7=new ImageIcon(".//min.png");
	    minBtn.setIcon(icon7);	      
	    minBtn.setBounds(715, 8, 24, 24);
	    minBtn.setBorderPainted(false);
        minBtn.setHorizontalAlignment(JButton.CENTER);
        minBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                setExtendedState(JFrame.ICONIFIED);//最小化窗体
               
            }
        });
        this.add(minBtn);
        
        this.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        	xOld = e.getX();//记录鼠标按下时的坐标
        	yOld = e.getY();
        	}
        });
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
        public void mouseDragged(MouseEvent e) {
        int xOnScreen = e.getXOnScreen();
        int yOnScreen = e.getYOnScreen();
        int xx = xOnScreen - xOld;
        int yy = yOnScreen - yOld;
        MyExtendsJFrame.this.setLocation(xx, yy);//设置拖拽后，窗口的位置
	    }
        });

    }

	//歌词预处理（txt类型）
	LinkedList<lirics> Liric_process(String song_name) 
	{
		 LinkedList<lirics> lir_list=new LinkedList<lirics>();
		 String path1 = ".//曲库";
	     File file = new File(path1);
	     File[] tempList = file.listFiles();
	     //System.out.println(tempList.length);
	     for (int i = 0; i < tempList.length; i++) {
	         if (tempList[i].isFile()) {
	        	 String fileName = tempList[i].getName();
	        	 String[] type_music = fileName.split("\\.");
	        	 if(type_music[1].equals("txt")) {
		        	 //System.out.println("文件名：" + fileName);
		        	 //System.out.println("文件类型：" + type_music[1]);
		        	 String[] song_name1 = song_name.split("\\.");
		        	 //System.out.println(type_music[0]);
		        	 //System.out.println(song_name1[0]);
		        	 //System.out.println(tempList[i].getPath());
		        	 String pathname = tempList[i].getPath();
	        		 if(type_music[0].equals(song_name1[0]))
	        		 {
	        			 //System.out.println(tempList[i].getPath());
	        			 //System.out.println("****************");
	        			 try {
		        			 File filename = new File(pathname); // 要读取以上路径的input。txt文件  
		        	         InputStreamReader reader = new InputStreamReader(  
		        	         new FileInputStream(filename)); // 建立一个输入流对象reader  
		        	         BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
		        	         String line = "";  
		        	         line = br.readLine();  
		        	         int flag=0,flag_ar=0,flag_al=0;;
		        	         int k=0;
		        	         String author="未知歌手",album="未知专辑";
		        	         while (line != null) {  
		        	        	 line = br.readLine(); // 一次读入一行数据
		        	        	 if(line.substring(1, 3).equals("ar")&&flag_ar==0)
		        	        	 {
		        	        		 flag_ar=1;
		        	        		 author=line.substring(4,line.length()-1);
		        	        		 System.out.println(author);
		        	        	 }
		        	        	 if(line.substring(1, 3).equals("al")&&flag_al==0)
		        	        	 {
		        	        		 flag_al=1;
		        	        		 album=line.substring(4,line.length()-1);
		        	        		 System.out.println(album);
		        	        		 String info = "歌手:"+author+"\n"+"专辑:"+album;
		        	        		 music_info.setText(info);
		        	        	 }
		        	        	 //System.out.println(line);
		        	        	 if(line.substring(1, 3).equals("00"))
		        	        	 {
		        	        		 flag=1;
		        	        	 }
		        	        	 if(flag==1)
		        	        	 {
		        	        		 
		        	        		 String minute= line.substring(1, 3);
			        	        	 String second= line.substring(4, 6);
			        	        	 String lir=line.substring(10,line.length());
			        	        	 //System.out.println(minute);
			        	        	 //System.out.println(second);
			        	        	 //System.out.println(lir);
			        	        	 //System.out.println("------------");
			        	        	 int minute1=Integer.parseInt(minute);
			        	        	 int second1=Integer.parseInt(second);
			        	        	 int time1=minute1*60+second1;
			        	        	 lir_list.add(new lirics(minute1,second1,lir,time1));
			        	        	 //System.out.println(lir_list.get(k).minute);
			        	        	 //System.out.println(lir_list.get(k).second);
			        	        	 //System.out.println(lir_list.get(k).line);
			        	        	 //System.out.println(lir_list.get(k).time);
			        	        	 //System.out.println("************");
			        	        	 k=k+1;
			        	        	 
		        	        	 }
		        	        	 
		        	        	 }
		        	         }  
		        			 
		        			 catch (Exception e) {  
		        	            e.printStackTrace();  }
	        		 }
	         }
	     }
	}
	     return lir_list;
	}

	
	//定时器函数
	lirics E=null;
	int lrc_num=0;//歌词句数
	int page=0;//歌词页数
	int down_limit=0;
	int up_limit=0;
	public  void timerFun(int nPlayTime,LinkedList<lirics> lir_list)
	{//定时器函数
		
		if(nTimer!=null){nTimer.cancel();}//已经有定时器则关闭
        nTimer = new Timer();//创建定时器
        nTimer.schedule(new TimerTask(){  //匿名类
        	int ttt=0;
        	int eachPlayTime=800/nPlayTime;
        	int starttime=0;
        	int minute=0;
            int second=0;
            int run_time=0;
            public void run() { //定时器函数体
            	playTime.setBounds(0, 500-43, starttime+=eachPlayTime, 3);
            	if(second==60)
            	{
            		minute=minute+1;
            		second=0;
            	}
            	String rt1=String.format("%02d",minute);
            	String rt2=String.format("%02d",second);
            	String rt=rt1+" : "+rt2;
            	
            	second=second+1;
            	runtime.setText(rt);
            	run_time=run_time+1;
            	//System.out.println(nPlayTime);
            	//System.out.println(second);
            	//System.out.println(lir_list.get(0).time);
            	
            	if(nPlayTime==run_time)
            	{
            		System.out.println("歌曲结束！");
            		playTime.setBounds(0, 500-43, 800, 3);
            		textLyrics2.setText("欢迎使用Zies播放器！");
            		
            	
            			int number1=mylist.size();
            			music E1=null;
            			for(int i=0;i<number1;i++)
            			{	 
            				 music mc=mylist.get(i);
            				 if(mc.song_name.equals(textarea.getText().toString()))
            				 {
            					E1= mc;
            					break;
            				 }
            			}
            			int Index1 = mylist.indexOf(E1);
            		if(mylist.get(Index1).status==1)
            		{
            			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index1).name);
        				audioPlay.play();
        				music mc=mylist.get(Index1);
        				File file=new File(mc.name); 
        	            LinkedList<lirics> lir_list=Liric_process(mc.song_name);
        				int iMusicTime;
        				 //处理老师样例文件和自定义文件(采样率不同 导致)
        				 String Judge = mc.song_name.substring(0, 2);
        				 System.out.println(Judge);
        				 if(Judge.equals("Eg"))
        				 {
        					 iMusicTime=(int)file.length()/1024/173;
        					 lir_list=null;
        					 textLyrics.setText(" ");
        					 music_info.setText(" ");
        				 }
        				 else 
        				 {iMusicTime=(int)file.length()/1024/23;}
        	            //System.out.println(iMusicTime);
        	            System.out.println(mc.song_name);
        	            lrc_num = 0;//每次切歌，歌词数记录清零
        	            page=0;//每次切歌，歌词页面数记录清零
        	            String rt12=String.format("%02d",mc.minute);
        	        	String rt22=String.format("%02d",mc.second);
        	        	String rt0="/"+rt12+" : "+rt22;
        	        	songtime.setText(rt0);
        				timerFun(iMusicTime,lir_list);//打开定时器，移动进度条
            		}
            		else
            		{
            			int number=mylist.size();
            			music E=null;
            			for(int i=0;i<number;i++)
            			{	 
            				 music mc=mylist.get(i);
            				 if(mc.song_name.equals(textarea.getText().toString()))
            				 {
            					E= mc;
            					break;
            				 }
            			}
            			int Index = mylist.indexOf(E);
            			//System.out.println(Index);
            			int end=mylist.size()-1;
            			if(Index==end)
            			{
            				Index=0;
            			}
            			else
            				Index=Index+1;
            			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name);//设置播放文件
            			audioPlay.play();//开始播放
            			music mc=mylist.get(Index);
            			textarea.setText(mc.song_name);//歌曲名更新
            			String[] song_name1=mc.song_name.split("\\.");
            			textarea2.setText(song_name1[0]);
            			//music mc1=mylist.get(0);
            			File file=new File(mc.name); 
            			System.out.println(mc.name);
            			LinkedList<lirics> lir_list=Liric_process(mc.song_name);
            			int iMusicTime;
            			 //处理老师样例文件和自定义文件(采样率不同 导致)
            			 String Judge = mc.song_name.substring(0, 2);
            			 System.out.println(Judge);
            			 if(Judge.equals("Eg"))
            			 {
            				 iMusicTime=(int)file.length()/1024/173;
            				 lir_list=null;
            				 textLyrics.setText(" ");
            				 music_info.setText(" ");
            			 }
            			 else 
            			 {iMusicTime=(int)file.length()/1024/23;}
                        System.out.println(mc.song_name);
                        lrc_num = 0;//每次切歌，歌词数记录清零
                        page=0;//每次切歌，歌词页面数记录清零
                        String rt122=String.format("%02d",mc.minute);
                    	String rt222=String.format("%02d",mc.second);
                    	String rt02="/"+rt122+" : "+rt222;
                    	songtime.setText(rt02);
            			timerFun(iMusicTime,lir_list);//更新计时器
            			buttonPlay.setVisible(false);
            		}
            		//textLyrics.setText("\n\n\n\n\n\n"+"当前还没有歌曲播放呢(￣▽￣)\n"+
                 	//		"赶紧点击右下角的\"电脑\"图标\n"+"选取您想欣赏的歌曲吧 ~\n"
    	    		//		+"小Z在这里等你哦O(∩_∩)O\n");//歌词控件添加文字
            		//music_info.setText(" ");
            		//buttonPlay.setVisible(true);
            		//buttonStop.setVisible(false);
            		this.cancel();
            	}
            	
            	//显示下端歌词
            	////////////////////一行一行打印在底部/////////
            	if(lir_list!=null)
            	{
            	textLyrics2.setText("");		//刷新歌词区
            	lirics lrc;
            	int number=lir_list.size();
    			//lirics stage;
    			int flag=0;//标志记录，某一句歌词占用几秒钟
    			for(int i=0;i<number;i++)
    			{	 
    				 flag=0;
    				 lrc=lir_list.get(i);
    				 //System.out.println(lrc.time);
    				 //System.out.println("-----");
    				 //System.out.println(run_time);
    				 if(lrc.time==run_time-1)
    				 {
    					E= lrc;
    					flag=1;
    					break;
    				 }
    			}
    			int Index = lir_list.indexOf(E);
            	
            	textLyrics2.setText(lir_list.get(Index).line);
            	}
            	else
            	{textLyrics2.setText("主人~当前曲目暂无歌词哦~");}
            	///////////////////////////////////////////////////////
            	
            	//    ***********************//
            	//主界面歌词显示（高亮）
            	//System.out.println(lir_list.size());
            	if(lir_list!=null) {
            	int n=lir_list.size();
            	String[] Lyrics=new String[n];
            	for(int i=0;i<n;i++)
            	{
            		Lyrics[i]=lir_list.get(i).line+"\n";
            	}
            	textLyrics.setText("");		//刷新歌词区
            	
            	int sec_per_page=15;//此参数可以根据页面设置
            	
            	if(run_time<=lir_list.get(n-1).time)
            	{
            	int flag_count=0;
            	for(int i=0+sec_per_page*page;i<sec_per_page+sec_per_page*page;i++)
            	{	//System.out.println(i);
            		if(i>=lir_list.size()) { 
            			System.out.println("STACKOVERFLOW");
            			break;
            		}
            		SimpleAttributeSet set1 ,set2;
	        		set1=new SimpleAttributeSet();
	        		set2=new SimpleAttributeSet();
	            	Document doc;
	            	doc=textLyrics.getDocument();
	            	StyleConstants.setForeground(set1, Color.red);
	            	StyleConstants.setForeground(set2, Color.LIGHT_GRAY);
	            	int index=0;
	            	if(flag_count==0) {
	            		for(int j=0+sec_per_page*page;j<sec_per_page+sec_per_page*page;j++)
		            	{
	            			if(j>=lir_list.size()) { 
	                			System.out.println("STACKOVERFLOW");
	                			break;
	                		}
		            		if(lir_list.get(j).time==run_time-1)
		            		{
		            			index=j;
		            			down_limit=lir_list.get(j).time;		            	
			            		up_limit=lir_list.get(j+1).time;
			            		System.out.println("--------");
			            		System.out.println(run_time);
			            		System.out.println(down_limit);
			            		System.out.println(up_limit);
			            		flag_count=1;
			            		lrc_num++;
			            		System.out.println(lrc_num);
			            		if(lrc_num==sec_per_page+sec_per_page*page) {page++;}
		            		}
		            		//System.out.println(lir_list.get(j).time);
		            		/*
		            		if(lir_list.get(j).time>=run_time-1)
		            		{
		            			System.out.println(lir_list.get(j).time);
		            			continue;
		            		}
		            		*/
		            		//down_limit=lir_list.get(j).time;
		            		//up_limit=lir_list.get(j+1).time;
		            		//break;
		            	}
	            	}
	            	if(run_time>=down_limit && run_time<=up_limit && i==lrc_num-1) {
						try {
							doc.insertString(doc.getLength(), Lyrics[i], set1);
							continue;
						} catch (BadLocationException e) {}
                	}
                	
                	try {
                		//System.out.println("第"+(i+1)+"行变绿");
                		//System.out.println(doc.getLength());
						doc.insertString(doc.getLength(), Lyrics[i], set2);
					} catch (BadLocationException e) {}
            	};
            	}
            	else
            	{
            		textLyrics.setText("~~~~~~~~~~~~~~~");
            	}
            	
               /* for(int i =0;i<Lyrics.length;i++){
                	//System.out.println(i);
                	//System.out.println(ttt);
	        		SimpleAttributeSet set1 ,set2;
	        		set1=new SimpleAttributeSet();
	        		set2=new SimpleAttributeSet();
	            	Document doc;
	            	doc=textLyrics.getDocument();
	            	StyleConstants.setForeground(set1, Color.red);
	            	StyleConstants.setForeground(set2, Color.LIGHT_GRAY);
                	if(ttt>=0 && ttt<=10 && i==0) {
						try {
							doc.insertString(doc.getLength(), Lyrics[i], set1);
							continue;
						} catch (BadLocationException e) {}
                	}
                	
                	try {
                		//System.out.println("第"+(i+1)+"行变绿");
                		//System.out.println(doc.getLength());
						doc.insertString(doc.getLength(), Lyrics[i], set2);
					} catch (BadLocationException e) {}
            	};
            	ttt++;*/
            	}
            	else {
                	textLyrics.setText("当前歌曲暂无歌词~\n请主人多多贡献歌词文件哦~");	
            	}
            	//              *****************// 
            	
            }
        },0,1000);
    }
	public  void timerStop () 
	{
		if(nTimer!=null){nTimer.cancel();}
		playTime.setBounds(0,324,0,3);
	}
	@SuppressWarnings("unchecked")//忽略警告
	int List_flag=0;
	int color_change=0;
	int color_layer=1;
	//按钮关联的事件函数如下
	public void actionPerformed(ActionEvent e)
	{	
		//点击播放按钮事件
		if(e.getSource()==buttonPlay){
			if(mylist.size()!=0)
			{
				Icon img1=new ImageIcon(".//唱片机2.gif");    
				gif.setVisible(false);
				gif = new JLabel(img1);
				gif.setBounds(380,112,400,270);
				add(gif);	
				gif.setVisible(true);
				buttonPlay.setVisible(false);
				buttonStop.setVisible(true);
				
				int number2=mylist.size();
    			music E2=null;
    			for(int i=0;i<number2;i++)
    			{	 
    				 music mc=mylist.get(i);
    				 if(mc.song_name.equals(textarea.getText().toString()))
    				 {
    					E2= mc;
    					break;
    				 }
    			}
    			int Index2 = mylist.indexOf(E2);
				
				audioPlay.SetPlayAudioPath("file:"+mylist.get(Index2).name);
				audioPlay.play();
				music mc=mylist.get(Index2);
				File file=new File(mc.name); 
	            LinkedList<lirics> lir_list=Liric_process(mc.song_name);
				int iMusicTime;
				 //处理老师样例文件和自定义文件(采样率不同 导致)
				 String Judge = mc.song_name.substring(0, 2);
				 System.out.println(Judge);
				 if(Judge.equals("Eg"))
				 {
					 iMusicTime=(int)file.length()/1024/173;
					 lir_list=null;
					 textLyrics.setText(" ");
					 music_info.setText(" ");
				 }
				 else 
				 {iMusicTime=(int)file.length()/1024/23;}
	            //System.out.println(iMusicTime);
	            System.out.println(mc.song_name);
	            lrc_num = 0;//每次切歌，歌词数记录清零
	            page=0;//每次切歌，歌词页面数记录清零
	            String rt1=String.format("%02d",mc.minute);
	        	String rt2=String.format("%02d",mc.second);
	        	String rt="/"+rt1+" : "+rt2;
	        	songtime.setText(rt);
				timerFun(iMusicTime,lir_list);//打开定时器，移动进度条
			}
			
		}
		//暂停 事件
		if(e.getSource()==buttonStop)
		{
			Icon img1=new ImageIcon(".//静止唱片机.png");    
			gif.setVisible(false);
			gif = new JLabel(img1);
			gif.setBounds(380,112,400,270);
			add(gif);	
			gif.setVisible(true);
			buttonPlay.setVisible(true);
			audioPlay.stop();
			timerStop();
		}
		//点击上一首按钮 事件
		if(e.getSource()==buttonPlay2)
		{
			//System.out.println(textarea.getText().toString());
			int number=mylist.size();
			music E=null;
			for(int i=0;i<number;i++)
			{	 
				 music mc=mylist.get(i);
				 if(mc.song_name.equals(textarea.getText().toString()))
				 {
					E= mc;
					break;
				 }
			}
			int Index = mylist.indexOf(E);
			//System.out.println(Index);
			int end=mylist.size()-1;
			if(Index==0)
			{
				Index=end;
			}
			else
				Index=Index-1;
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name);//设置播放文件
			audioPlay.play();//开始播放
			music mc=mylist.get(Index);
			textarea.setText(mc.song_name);//歌曲名更新
			//System.out.println(mc.song_name);
			String[] song_name1=mc.song_name.split("\\.");
			textarea2.setText(song_name1[0]);
			//music mc1=mylist.get(0);
			File file=new File(mc.name); 
			LinkedList<lirics> lir_list=Liric_process(mc.song_name);
			int iMusicTime;
			 //处理老师样例文件和自定义文件(采样率不同 导致)
			 String Judge = mc.song_name.substring(0, 2);
			 //System.out.println(Judge);
			 if(Judge.equals("Eg"))
			 {
				 iMusicTime=(int)file.length()/1024/173;
				 lir_list=null;
				 textLyrics.setText(" ");
				 music_info.setText(" ");
			 }
			 else 
			 {iMusicTime=(int)file.length()/1024/23;}
             //System.out.println(mc.song_name);
			 lrc_num = 0;//每次切歌，歌词数记录清零
	         page=0;//每次切歌，歌词页面数记录清零
	         String rt1=String.format("%02d",mc.minute);
	        	String rt2=String.format("%02d",mc.second);
	        	String rt="/"+rt1+" : "+rt2;
	        	songtime.setText(rt);
			timerFun(iMusicTime,lir_list);//更新计时器
			buttonPlay.setVisible(false);
		}
		//点击下一首按钮 事件
		if(e.getSource()==buttonPlay1)
		{
			//System.out.println(textarea.getText().toString());
			int number=mylist.size();
			music E=null;
			for(int i=0;i<number;i++)
			{	 
				 music mc=mylist.get(i);
				 if(mc.song_name.equals(textarea.getText().toString()))
				 {
					E= mc;
					break;
				 }
			}
			int Index = mylist.indexOf(E);
			//System.out.println(Index);
			int end=mylist.size()-1;
			if(Index==end)
			{
				Index=0;
			}
			else
				Index=Index+1;
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name);//设置播放文件
			audioPlay.play();//开始播放
			music mc=mylist.get(Index);
			textarea.setText(mc.song_name);//歌曲名更新
			String[] song_name1=mc.song_name.split("\\.");
			textarea2.setText(song_name1[0]);
			//music mc1=mylist.get(0);
			File file=new File(mc.name); 
			System.out.println(mc.name);
			LinkedList<lirics> lir_list=Liric_process(mc.song_name);
			int iMusicTime;
			 //处理老师样例文件和自定义文件(采样率不同 导致)
			 String Judge = mc.song_name.substring(0, 2);
			 System.out.println(Judge);
			 if(Judge.equals("Eg"))
			 {
				 iMusicTime=(int)file.length()/1024/173;
				 lir_list=null;
				 textLyrics.setText(" ");
				 music_info.setText(" ");
			 }
			 else 
			 {iMusicTime=(int)file.length()/1024/23;}
            System.out.println(mc.song_name);
            lrc_num = 0;//每次切歌，歌词数记录清零
            page=0;//每次切歌，歌词页面数记录清零
            String rt1=String.format("%02d",mc.minute);
        	String rt2=String.format("%02d",mc.second);
        	String rt="/"+rt1+" : "+rt2;
        	songtime.setText(rt);
			timerFun(iMusicTime,lir_list);//更新计时器
			buttonPlay.setVisible(false);
		}
		//点击打开文件按钮 事件
		 Vector  vt=new Vector ();	//创建Vector对象，通过add方法添加多行
		if(e.getSource()==buttonPlay3){
			Icon img1=new ImageIcon(".//唱片机2.gif");    
			gif.setVisible(false);
			gif = new JLabel(img1);
			gif.setBounds(380,112,400,270);
			add(gif);	
			 FileDialog openFile=new FileDialog(this,"打开文件");//创建打开文件对话框			
			 openFile.setVisible(true);//对话框可见
			 String playFileName=openFile.getFile();//获取打开的文件名
			 if(playFileName.isEmpty())
			 {System.out.println("没有选中文件");}
			 //System.out.println(playFileName);
			 String playFileDirectory=openFile.getDirectory();//获取打开的文件路径
			 String playFile_path=playFileDirectory+playFileName;//完整的路径+文件名
			 audioPlay.SetPlayAudioPath("file:"+playFile_path);//设置播放文件
			 audioPlay.play();//开始播放		 
			 
			 textarea.setText(playFileName);//歌曲名更新
			 System.out.println(playFileName);
			 LinkedList<lirics> lir_list=Liric_process(playFileName);
			 String[] song_name1=playFileName.split("\\.");
			 textarea2.setText(song_name1[0]);
			 File file=new File(playFile_path); 
			 
			 int iMusicTime;
			 //处理老师样例文件和自定义文件(采样率不同 导致)
			 String Judge = playFileName.substring(0, 2);
			 System.out.println(Judge);
			 if(Judge.equals("Eg"))
			 {
				 iMusicTime=(int)file.length()/1024/173;
				 lir_list=null;
				 textLyrics.setText(" ");
				 music_info.setText(" ");
			 }
			 else 
			 {iMusicTime=(int)file.length()/1024/23;}
	         
	         //System.out.println("文件路径？"+file.getPath());
	         //System.out.println("文件名称？"+file.getName());
	         //System.out.println("是文件吗？"+file.isFile());
	         //System.out.println("文件长度："+file.length());
	         //System.out.println("音乐时间："+iMusicTime);
	         //System.out.println("------------------------------------");
			 lrc_num = 0;//每次切歌，歌词数记录清零
	         page=0;//每次切歌，歌词页面数记录清零
			 timerFun(iMusicTime,lir_list);//更新计时器
			 buttonPlay.setVisible(false);
			 buttonStop.setVisible(true);
			 int number=mylist.size();
			// for(int i=0;i<number;i++)
			 //{	 
				// music mc=mylist.get(i);
				 //if(playFileName.equals(mc.song_name))
				 //{
					 //mylist.remove(i); 
					 //vt.remove(number-i);
					 //listPlayFile.setListData(vt);
					 //break;
				 //}
			 //}
			 int minute=iMusicTime/60;
			 int second=iMusicTime-60*minute;
			 mylist.addFirst(new music(playFile_path,iMusicTime,playFileName,minute,second,0));
			 String rt1=String.format("%02d",minute);
	        	String rt2=String.format("%02d",second);
	        	String rt="/"+rt1+" : "+rt2;
	        	songtime.setText(rt);
			 int number0=mylist.size();
			 for(int k=0;k<number0;k++)
			 {
				 int j=k+1;
				 vt.add(j+"、"+mylist.get(k).song_name+" "+mylist.get(k).minute+":"+mylist.get(k).second);
				 //System.out.println(mylist.get(0).song_name+" "+mylist.get(0).time);
			 }
			 
			 vt1=vt;
			 listPlayFile.setListData(vt);
			 
		}
		
		if(e.getSource()==buttonPlay4)
		{
			int number=mylist.size();
			music E=null;
			for(int i=0;i<number;i++)
			{	 
				 music mc=mylist.get(i);
				 if(mc.song_name.equals(textarea.getText().toString()))
				 {
					E= mc;
					break;
				 }
			}
			int Index = mylist.indexOf(E);
			System.out.println(Index);
			if(mylist.get(Index).status==0)
			{
				mylist.get(Index).status=1;
				Icon icon_loop=new ImageIcon(".//loop1.png");
				buttonPlay4.setIcon(icon_loop);	
				add(buttonPlay4);
			}
			else 
			{
				mylist.get(Index).status=0;
				Icon icon_loop=new ImageIcon(".//loop.png");
				buttonPlay4.setIcon(icon_loop);	
				add(buttonPlay4);
			}
			//System.out.println(mylist.get(0).status);
		}
		
		//点击播放列表按钮 事件（显示/消失）
		if(e.getSource()==buttonList)
		{
			System.out.println(List_flag);
			if(List_flag==0)
			{
				listPlayFile.setVisible(true);
				textLyrics.setVisible(false);
				List_flag=1;
				
			}
			else
			{
				listPlayFile.setVisible(false);
				textLyrics.setVisible(true);
				List_flag=0;
			}
			
		}
		
		if(e.getSource()==change_color) {
			if(color_change==0)
		    {
				color_layer++;
				color_change=1;
		    	Icon imgu2=new ImageIcon(".//上部1.png");     
				upground = new JLabel(imgu2);
				upground.setBounds(0,0,800,41);
			    getLayeredPane().add(upground, new Integer(Integer.MIN_VALUE+color_layer));
				((JPanel)getContentPane()).setOpaque(false); 
				Icon img_t=new ImageIcon(".//图标2.jpg");    	
				tubiao = new JLabel(img_t);
				tubiao.setBounds(0,0,183,41);
			    getLayeredPane().add(tubiao, new Integer(Integer.MIN_VALUE+1+color_layer));
				((JPanel)getContentPane()).setOpaque(false);
				Icon icon_close=new ImageIcon(".//close2.png");
			    closeBtn.setIcon(icon_close);	    
			    Icon icon_max=new ImageIcon(".//max2.png");
			    maxBtn.setIcon(icon_max);	  
			    Icon icon_min=new ImageIcon(".//min2.png");
			    minBtn.setIcon(icon_min);	
			    Icon icon_color=new ImageIcon(".//皮肤2.jpg");
			    change_color.setIcon(icon_color);	
			    Icon icon_last=new ImageIcon(".//last2.png");
			    buttonPlay2.setIcon(icon_last);	
			    Icon icon_next=new ImageIcon(".//next2.png");
			    buttonPlay1.setIcon(icon_next);	
			    Icon icon_start=new ImageIcon(".//start2.png");
			    buttonPlay.setIcon(icon_start);
			    Icon icon_stop=new ImageIcon(".//stop2.png");
			    buttonStop.setIcon(icon_stop);	
			    textLyrics.setForeground(Color.green);//歌词控件字体颜色
			    textLyrics.setFont(new Font("隶书",Font.BOLD,15));
			    add(change_color);
		    }
		    else 
		    {
		    	color_layer++;
				color_change=0;
				Icon imgu2=new ImageIcon(".//上部.png");     
				upground = new JLabel(imgu2);
				upground.setBounds(0,0,800,41);
			    getLayeredPane().add(upground, new Integer(Integer.MIN_VALUE+color_layer));
				((JPanel)getContentPane()).setOpaque(false); 
				Icon img_t=new ImageIcon(".//图标.jpg");    	
				tubiao = new JLabel(img_t);
				tubiao.setBounds(0,0,183,41);
			    getLayeredPane().add(tubiao, new Integer(Integer.MIN_VALUE+1+color_layer));
				((JPanel)getContentPane()).setOpaque(false);
				Icon icon_close=new ImageIcon(".//close.png");
			    closeBtn.setIcon(icon_close);	    
			    Icon icon_max=new ImageIcon(".//max.png");
			    maxBtn.setIcon(icon_max);	  
			    Icon icon_min=new ImageIcon(".//min.png");
			    minBtn.setIcon(icon_min);	
			    Icon icon_color=new ImageIcon(".//皮肤1.jpg");
			    change_color.setIcon(icon_color);	
			    Icon icon_last=new ImageIcon(".//last.png");
			    buttonPlay2.setIcon(icon_last);	
			    Icon icon_next=new ImageIcon(".//next.png");
			    buttonPlay1.setIcon(icon_next);	
			    Icon icon_start=new ImageIcon(".//start.png");
			    buttonPlay.setIcon(icon_start);
			    Icon icon_stop=new ImageIcon(".//stop.png");
			    buttonStop.setIcon(icon_stop);	
			    add(change_color);
		    }
			}
	}
	
	
	    public void mousePressed(MouseEvent e){}//按下鼠标
		public void mouseReleased(MouseEvent e){}//释放鼠标
		public void mouseEntered(MouseEvent e){}//鼠标进入
		public void  mouseExited(MouseEvent e){}//鼠标离开
		public void  mouseClicked(MouseEvent e){//点击鼠标		
			// 双击播放列表歌曲 事件
			 if (e.getClickCount() == 2) {//如果鼠标连续点击两次
				 if(e.getSource()==listPlayFile){//如果事件源是播放列表，即在播放列表控件中双击，则执行。
				 //添加双击播放列表中的代码，比如获取歌曲名，并且播放。
					 int index=listPlayFile.getSelectedIndex();
					 //System.out.println(index);
					 String str=vt1.get(index);
					 System.out.println(str);
					 String[] str1 = str.split(" ");
					 System.out.println(str1[0]);
					 str=str1[0].substring(2);
					 int number=mylist.size();
						music E=null;
						for(int i=0;i<number;i++)
						{	 
							 music mc=mylist.get(i);
							 if(mc.song_name.equals(str))
							 {
								E= mc;
								break;
							 }
						}
						//int Index = mylist.indexOf(E);
					 //System.out.println(str);
					 textarea.setText(str);//歌曲名更新
					 String[] song_name1=str.split("\\.");
					 textarea2.setText(song_name1[0]);
					 audioPlay.SetPlayAudioPath("file:"+E.name);//设置播放文件
					 System.out.println(E.name);
					 audioPlay.play();//开始播放
					 
					 File file=new File(mylist.get(index).name); 
					 System.out.println(file.getName());

			         System.out.println(str);
			         LinkedList<lirics> lir_list=Liric_process(str);
			         int iMusicTime;
					 //处理老师样例文件和自定义文件(采样率不同 导致)
					 String Judge = str.substring(0, 2);
					 System.out.println(Judge);
					 if(Judge.equals("Eg"))
					 {
						 iMusicTime=(int)file.length()/1024/173;
						 lir_list=null;
						 textLyrics.setText(" ");
						 music_info.setText(" ");
					 }
					 else 
					 {iMusicTime=(int)file.length()/1024/23;}
					 lrc_num = 0;//每次切歌，歌词数记录清零
			         page=0;//每次切歌，歌词页面数记录清零
			         String rt1=String.format("%02d",E.minute);
			        	String rt2=String.format("%02d",E.second);
			        	String rt="/"+rt1+" : "+rt2;
			        	songtime.setText(rt);
					 timerFun(iMusicTime,lir_list);//更新计时器
					 buttonPlay.setVisible(false);
				 } 
			 }	
	    }
}


//主函数
public class musicplayer{
@SuppressWarnings("unchecked")//忽略警告
    	public static void main(String[] args) { 
        MyExtendsJFrame frame=new MyExtendsJFrame();//创建聊天程序窗口  
        frame.setUndecorated(true);
        frame.setVisible(true);//放在添加组件后面执行
    }
}
