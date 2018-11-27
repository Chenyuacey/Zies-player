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

class music//������Ϣ��
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


class lirics//�����
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

class audioplay{//����������
	   AudioClip adc;// ������Ƶ��������
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
	   void play(){     //��ʼ����
	           adc.play();
	           playFlag=true;
	   }   
	   void stop(){     //ֹͣ����
	           adc.stop();   
	           playFlag=false;
	   }
	}

class MyExtendsJFrame extends JFrame implements ActionListener,MouseListener
{ //������
	JLabel background;//�����ؼ�
	JLabel underground;//�²�
	JLabel upground;//�ϲ�
	JLabel tubiao;//������ͼ��
	JButton buttonPlay;//���� ��ť
	JButton buttonStop;//ֹͣ����
	JButton buttonPlay1;//��һ�� ��ť
	JButton buttonPlay2;//��һ�� ��ť
	JButton buttonPlay3;//�� ��ť
	JButton buttonPlay4;// ѭ�� ��ť
	JButton buttonList;//�����б���ʾ/���� ��ť
	JTextPane textLyrics;//�����ʿؼ�
    JTextArea textLyrics2;//�¶˸�ʿؼ�
    JTextArea music_info;//������Ϣ��������+ר������
	JLabel playTime;//���Ž������ؼ�
	JList listPlayFile;//�����б�ؼ�
	Timer nTimer;//��ʱ������
	JTextArea textarea;//�����ؼ�
	JTextArea textarea2;//�����ؼ�2
	JTextArea runtime;//��ǰ����ʱ��
	JTextArea songtime;//������ʱ��
	JLabel gif;//��ͼ
	JLabel album_pic;//ר��ͼƬ
	audioplay audioPlay;
	LinkedList<music>mylist;//�����б�
	Vector<String> vt1=new Vector ();//������ΧVector�������ڵ�������б����������
	JButton closeBtn = null;
	JButton maxBtn = null;
	JButton minBtn = null; 
	JButton change_color;
	int xOld = 0;
	int yOld = 0;
	public MyExtendsJFrame()
	{
		audioPlay=new audioplay();  //�������Ŷ���
		mylist=new LinkedList<music>();	
		setTitle("������");//�����
		setBounds(160,100,800,500);	//���ô��ڴ�С		
		setLayout(null);//�ղ���	
		init();   //��ӿؼ��Ĳ�����װ��һ������         
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	void init()
	{//��ӵĿؼ�
		//���ñ���ͼƬ
		Icon img=new ImageIcon(".//timg1.jpg");     //����ͼ�����			
		background = new JLabel(img);//���ñ���ͼƬ
		background.setBounds(0,0,800,500);//���ñ����ؼ���С
	    getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//����ͼƬ�ؼ�������ײ�
		((JPanel)getContentPane()).setOpaque(false); //�ؼ�͸��
		//�����ϱ߿�		
		Icon imgu=new ImageIcon(".//�ϲ�.png");     
		upground = new JLabel(imgu);
		upground.setBounds(0,0,800,41);
	    getLayeredPane().add(upground, new Integer(Integer.MIN_VALUE+1));
		((JPanel)getContentPane()).setOpaque(false); 
		//�����±߿�
		Icon img_d=new ImageIcon(".//����.png");    	
		underground = new JLabel(img_d);
		underground.setBounds(0,500-41,800,41);
	    getLayeredPane().add(underground, new Integer(Integer.MIN_VALUE+1));
		((JPanel)getContentPane()).setOpaque(false); 
		//��������ͼ��
		Icon img_t=new ImageIcon(".//ͼ��.jpg");    	
		tubiao = new JLabel(img_t);
		tubiao.setBounds(0,0,183,41);
	    getLayeredPane().add(tubiao, new Integer(Integer.MIN_VALUE+2));
		((JPanel)getContentPane()).setOpaque(false); 		
		//���ö�̬ͼ
		Icon img1=new ImageIcon(".//��ֹ��Ƭ��.png");     	
		gif = new JLabel(img1);
		gif.setBounds(380,112,400,270);
		add(gif);	      
		//���õ�ǰ��������ʱ��
	    runtime=new JTextArea("00 : 00");
	    runtime.setBounds(500,500-38,40,25);
	    runtime.setForeground(Color.MAGENTA);//��ʿؼ�������ɫ
	    runtime.setOpaque(false);
	    add(runtime);	
	    /*
	     * ����ר��ͼƬ
	     * ʧ�ܡ���gif֮��
	    Icon img_album=new ImageIcon(".//Vk-ɽ��С¥ҹ����.png");     	
		album_pic = new JLabel(img_album);
		album_pic.setBounds(380,112,150,270);
		getLayeredPane().add(album_pic, new Integer(Integer.MIN_VALUE+2));
		add(album_pic);	
	    */
	    //���ø�����ʱ��
	    songtime=new JTextArea("/00 : 00");
	    songtime.setBounds(540,500-38,50,25);
	    songtime.setForeground(Color.MAGENTA);//��ʿؼ�������ɫ
	    songtime.setOpaque(false);
	    add(songtime);	

	    //���ò��Ű�ť
		buttonPlay=new JButton();//���     ����   ��ť
	    buttonPlay.setBounds(45,500-40,38,38); //���ò��Ű�ť��С
	    Icon icon=new ImageIcon(".//start.png");//��������ͼ�����
	    buttonPlay.setIcon(icon);	      //���ò���ͼ��
	    buttonPlay.setBorderPainted(false);
	    buttonPlay.addActionListener(this);//��ӵ��������¼�
	    add(buttonPlay);//��Ӳ��Ű�ť��������	    
	    //����ֹͣ��ť
	    buttonStop=new JButton();
	    buttonStop.setBounds(45,500-40,38,38);
	    Icon iconstop=new ImageIcon(".//stop.png");
	    buttonStop.setIcon(iconstop);	     
	    buttonStop.setBorderPainted(false);
	    buttonStop.addActionListener(this);
	    add(buttonStop);	      
	    //������һ�װ�ť
	    buttonPlay1=new JButton();//���    ��һ��    ��ť
	    buttonPlay1.setBounds(90,500-38,35,35); 
	    Icon icon1=new ImageIcon(".//next.png");
	    buttonPlay1.setIcon(icon1);	     
	    buttonPlay1.setBorderPainted(false);
	    buttonPlay1.addActionListener(this);
	    add(buttonPlay1);      
	    //������һ�װ�ť
	    buttonPlay2=new JButton();
	    buttonPlay2.setBounds(3,500-38,35,35);
	    Icon icon2=new ImageIcon(".//last.png");
	    buttonPlay2.setIcon(icon2);	      
	    buttonPlay2.setBorderPainted(false);
	    buttonPlay2.addActionListener(this);
	    add(buttonPlay2);      
	    //���ô��ļ���ť
	    buttonPlay3=new JButton();
	    buttonPlay3.setBounds(800-36-40-40,500-33-4,36,33); 
	    Icon icon3=new ImageIcon(".//open.png");
	    buttonPlay3.setIcon(icon3);	      
	    buttonPlay3.setBorderPainted(false);
	    buttonPlay3.addActionListener(this);
	    add(buttonPlay3);   
	    //����ѭ����ť
	    buttonPlay4=new JButton();
	    buttonPlay4.setBounds(140,500-33-4,36,33); 
	    Icon icon4=new ImageIcon(".//loop.png");
	    buttonPlay4.setIcon(icon4);	      
	    buttonPlay4.setBorderPainted(false);
	    buttonPlay4.addActionListener(this);
	    add(buttonPlay4);
	    //���ò����б�ť����ʾ����ʧ��    
	    buttonList=new JButton();
	    buttonList.setBounds(800-36-35,500-33-2,36,33); 
	    Icon icon_list=new ImageIcon(".//list.png");
	    buttonList.setIcon(icon_list);	      
	    buttonList.setBorderPainted(false);
	    buttonList.addActionListener(this);
	    add(buttonList);
	    //�¶���ʾ������
	    textarea= new JTextArea(" ");
	    textarea.setBounds(200,500-38,450,25);
	    textarea.setForeground(Color.BLACK);//��ʿؼ�������ɫ
	    getLayeredPane().add(textarea, new Integer(Integer.MIN_VALUE+3));
	    textarea.setOpaque(false);
	    add(textarea);
	    //���ò����б�
	    listPlayFile=new JList();	  //���������б� 
	    listPlayFile.setBounds(100,120,250,380); //���ò����б��С 
	    listPlayFile.setOpaque(true);//���ò����б�͸��
	    listPlayFile.setBackground(new Color(0, 0, 0, 0));//���ò����б�����ɫ
	    listPlayFile.setForeground(Color.white);//���ò����б�������ɫ
	    add(listPlayFile);       //��Ӳ����б���������
	    listPlayFile.addMouseListener(this);//��Ӳ����б��˫���¼�����
	    listPlayFile.setVisible(false); 
	    listPlayFile.setVisible(false); 
	    //���ø�����ʾ����ҳ�棩
	    textarea2= new JTextArea(" ");
	    textarea2.setBounds(100,50,250,25);
	    textarea2.setForeground(Color.CYAN);
	    textarea2.setFont(new Font("����",Font.BOLD,20));
	    getLayeredPane().add(textarea2, new Integer(Integer.MIN_VALUE+3));
	    textarea2.setOpaque(false);
	    add(textarea2);	  
	    //���ø�����Ϣ��ʾ
	    music_info = new JTextArea(" ");
	    music_info.setBounds(100,75,250,35);
	    music_info.setForeground(Color.green);
	    music_info.setFont(new Font("����",Font.BOLD,12));
	    getLayeredPane().add(music_info, new Integer(Integer.MIN_VALUE+3));
	    music_info.setOpaque(false);
	    add(textarea2);	  
	    //����������ʾ
	    textLyrics=new JTextPane();   //������ʿؼ�    
	    //JScrollPane scrollPane = new JScrollPane(textLyrics);
	    //scrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
	    textLyrics.setBounds(100,120,250,315);//���ø�ʿؼ���С
	    textLyrics.setForeground(Color.LIGHT_GRAY);//��ʿؼ�������ɫ
	    textLyrics.setFont(new Font("����",Font.BOLD,15));
	    textLyrics.setOpaque(false);//��ʿؼ�͸��
	    add(textLyrics);    //��Ӹ�ʿؼ���������
	    //add(scrollPane);
	    textLyrics.setText("\n\n\n\n\n"+"��ǰ��û�и���������(������)\n"+
             	 		"�Ͻ�������½ǵ�\"����\"ͼ��\n"+"ѡȡ�������͵ĸ����� ~\n"
	    				+"СZ���������ŶO(��_��)O\n");//��ʿؼ��������
	    	    
	    //�����¶˸�ʣ�������ʾ��
	    textLyrics2=new JTextArea(" ");   //������ʿؼ�    
	    textLyrics2.setBounds(300,500-17,250,15);//���ø�ʿؼ���С
	    textLyrics2.setForeground(Color.BLUE);//��ʿؼ�������ɫ
	    textLyrics2.setFont(new Font("����",Font.BOLD,15));
	    textLyrics2.setOpaque(false);//��ʿؼ�͸��
	    add(textLyrics2);    //��Ӹ�ʿؼ���������
	    //add(scrollPane);
	    textLyrics2.setText("��ӭʹ��Zies��������");//��ʿؼ��������	      
	    //���ý�����
	    Icon img2=new ImageIcon(".//jindu.png");     //����ͼ�����
	    playTime = new JLabel(img2);	  		//�������Ž���������
	    playTime.setBounds(0,500-43,0,3);	  	//���ò��Ž����������С	      
	    add(playTime); //��Ӳ��Ž�������������
	    //���û�����ť
	    change_color = new JButton("");
	    Icon icon_color=new ImageIcon(".//Ƥ��1.jpg");
	    change_color.setIcon(icon_color);	      
	    change_color.setBounds(685, 10, 24, 23);
	    change_color.setBorderPainted(false);
	    change_color.setHorizontalAlignment(JButton.CENTER);
	    change_color.addActionListener(this);
	    add(change_color);
	    
	    //���ùرհ�ť
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
	            //ʹ��dispose();Ҳ���Թر�ֻ�ǲ��������Ĺر�
	        }
	    });
	    this.add(closeBtn);
	    //������󻯰�ť
	    maxBtn = new JButton("");
	    Icon icon6=new ImageIcon(".//max.png");
	    maxBtn.setIcon(icon6);	      
	    maxBtn.setBounds(745, 8, 24, 24);
	    maxBtn.setBorderPainted(false);
        maxBtn.setHorizontalAlignment(JButton.CENTER);
        maxBtn.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                setExtendedState(JFrame.MAXIMIZED_BOTH);//��󻯴���
               
            }
        });
        this.add(maxBtn);
        //������С����ť
        minBtn = new JButton("");
        Icon icon7=new ImageIcon(".//min.png");
	    minBtn.setIcon(icon7);	      
	    minBtn.setBounds(715, 8, 24, 24);
	    minBtn.setBorderPainted(false);
        minBtn.setHorizontalAlignment(JButton.CENTER);
        minBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                setExtendedState(JFrame.ICONIFIED);//��С������
               
            }
        });
        this.add(minBtn);
        
        this.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        	xOld = e.getX();//��¼��갴��ʱ������
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
        MyExtendsJFrame.this.setLocation(xx, yy);//������ק�󣬴��ڵ�λ��
	    }
        });

    }

	//���Ԥ����txt���ͣ�
	LinkedList<lirics> Liric_process(String song_name) 
	{
		 LinkedList<lirics> lir_list=new LinkedList<lirics>();
		 String path1 = ".//����";
	     File file = new File(path1);
	     File[] tempList = file.listFiles();
	     //System.out.println(tempList.length);
	     for (int i = 0; i < tempList.length; i++) {
	         if (tempList[i].isFile()) {
	        	 String fileName = tempList[i].getName();
	        	 String[] type_music = fileName.split("\\.");
	        	 if(type_music[1].equals("txt")) {
		        	 //System.out.println("�ļ�����" + fileName);
		        	 //System.out.println("�ļ����ͣ�" + type_music[1]);
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
		        			 File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�  
		        	         InputStreamReader reader = new InputStreamReader(  
		        	         new FileInputStream(filename)); // ����һ������������reader  
		        	         BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
		        	         String line = "";  
		        	         line = br.readLine();  
		        	         int flag=0,flag_ar=0,flag_al=0;;
		        	         int k=0;
		        	         String author="δ֪����",album="δ֪ר��";
		        	         while (line != null) {  
		        	        	 line = br.readLine(); // һ�ζ���һ������
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
		        	        		 String info = "����:"+author+"\n"+"ר��:"+album;
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

	
	//��ʱ������
	lirics E=null;
	int lrc_num=0;//��ʾ���
	int page=0;//���ҳ��
	int down_limit=0;
	int up_limit=0;
	public  void timerFun(int nPlayTime,LinkedList<lirics> lir_list)
	{//��ʱ������
		
		if(nTimer!=null){nTimer.cancel();}//�Ѿ��ж�ʱ����ر�
        nTimer = new Timer();//������ʱ��
        nTimer.schedule(new TimerTask(){  //������
        	int ttt=0;
        	int eachPlayTime=800/nPlayTime;
        	int starttime=0;
        	int minute=0;
            int second=0;
            int run_time=0;
            public void run() { //��ʱ��������
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
            		System.out.println("����������");
            		playTime.setBounds(0, 500-43, 800, 3);
            		textLyrics2.setText("��ӭʹ��Zies��������");
            		
            	
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
        				 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
        	            lrc_num = 0;//ÿ���и裬�������¼����
        	            page=0;//ÿ���и裬���ҳ������¼����
        	            String rt12=String.format("%02d",mc.minute);
        	        	String rt22=String.format("%02d",mc.second);
        	        	String rt0="/"+rt12+" : "+rt22;
        	        	songtime.setText(rt0);
        				timerFun(iMusicTime,lir_list);//�򿪶�ʱ�����ƶ�������
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
            			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name);//���ò����ļ�
            			audioPlay.play();//��ʼ����
            			music mc=mylist.get(Index);
            			textarea.setText(mc.song_name);//����������
            			String[] song_name1=mc.song_name.split("\\.");
            			textarea2.setText(song_name1[0]);
            			//music mc1=mylist.get(0);
            			File file=new File(mc.name); 
            			System.out.println(mc.name);
            			LinkedList<lirics> lir_list=Liric_process(mc.song_name);
            			int iMusicTime;
            			 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
                        lrc_num = 0;//ÿ���и裬�������¼����
                        page=0;//ÿ���и裬���ҳ������¼����
                        String rt122=String.format("%02d",mc.minute);
                    	String rt222=String.format("%02d",mc.second);
                    	String rt02="/"+rt122+" : "+rt222;
                    	songtime.setText(rt02);
            			timerFun(iMusicTime,lir_list);//���¼�ʱ��
            			buttonPlay.setVisible(false);
            		}
            		//textLyrics.setText("\n\n\n\n\n\n"+"��ǰ��û�и���������(������)\n"+
                 	//		"�Ͻ�������½ǵ�\"����\"ͼ��\n"+"ѡȡ�������͵ĸ����� ~\n"
    	    		//		+"СZ���������ŶO(��_��)O\n");//��ʿؼ��������
            		//music_info.setText(" ");
            		//buttonPlay.setVisible(true);
            		//buttonStop.setVisible(false);
            		this.cancel();
            	}
            	
            	//��ʾ�¶˸��
            	////////////////////һ��һ�д�ӡ�ڵײ�/////////
            	if(lir_list!=null)
            	{
            	textLyrics2.setText("");		//ˢ�¸����
            	lirics lrc;
            	int number=lir_list.size();
    			//lirics stage;
    			int flag=0;//��־��¼��ĳһ����ռ�ü�����
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
            	{textLyrics2.setText("����~��ǰ��Ŀ���޸��Ŷ~");}
            	///////////////////////////////////////////////////////
            	
            	//    ***********************//
            	//����������ʾ��������
            	//System.out.println(lir_list.size());
            	if(lir_list!=null) {
            	int n=lir_list.size();
            	String[] Lyrics=new String[n];
            	for(int i=0;i<n;i++)
            	{
            		Lyrics[i]=lir_list.get(i).line+"\n";
            	}
            	textLyrics.setText("");		//ˢ�¸����
            	
            	int sec_per_page=15;//�˲������Ը���ҳ������
            	
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
                		//System.out.println("��"+(i+1)+"�б���");
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
                		//System.out.println("��"+(i+1)+"�б���");
                		//System.out.println(doc.getLength());
						doc.insertString(doc.getLength(), Lyrics[i], set2);
					} catch (BadLocationException e) {}
            	};
            	ttt++;*/
            	}
            	else {
                	textLyrics.setText("��ǰ�������޸��~\n�����˶�๱�׸���ļ�Ŷ~");	
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
	@SuppressWarnings("unchecked")//���Ծ���
	int List_flag=0;
	int color_change=0;
	int color_layer=1;
	//��ť�������¼���������
	public void actionPerformed(ActionEvent e)
	{	
		//������Ű�ť�¼�
		if(e.getSource()==buttonPlay){
			if(mylist.size()!=0)
			{
				Icon img1=new ImageIcon(".//��Ƭ��2.gif");    
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
				 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
	            lrc_num = 0;//ÿ���и裬�������¼����
	            page=0;//ÿ���и裬���ҳ������¼����
	            String rt1=String.format("%02d",mc.minute);
	        	String rt2=String.format("%02d",mc.second);
	        	String rt="/"+rt1+" : "+rt2;
	        	songtime.setText(rt);
				timerFun(iMusicTime,lir_list);//�򿪶�ʱ�����ƶ�������
			}
			
		}
		//��ͣ �¼�
		if(e.getSource()==buttonStop)
		{
			Icon img1=new ImageIcon(".//��ֹ��Ƭ��.png");    
			gif.setVisible(false);
			gif = new JLabel(img1);
			gif.setBounds(380,112,400,270);
			add(gif);	
			gif.setVisible(true);
			buttonPlay.setVisible(true);
			audioPlay.stop();
			timerStop();
		}
		//�����һ�װ�ť �¼�
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
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name);//���ò����ļ�
			audioPlay.play();//��ʼ����
			music mc=mylist.get(Index);
			textarea.setText(mc.song_name);//����������
			//System.out.println(mc.song_name);
			String[] song_name1=mc.song_name.split("\\.");
			textarea2.setText(song_name1[0]);
			//music mc1=mylist.get(0);
			File file=new File(mc.name); 
			LinkedList<lirics> lir_list=Liric_process(mc.song_name);
			int iMusicTime;
			 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
			 lrc_num = 0;//ÿ���и裬�������¼����
	         page=0;//ÿ���и裬���ҳ������¼����
	         String rt1=String.format("%02d",mc.minute);
	        	String rt2=String.format("%02d",mc.second);
	        	String rt="/"+rt1+" : "+rt2;
	        	songtime.setText(rt);
			timerFun(iMusicTime,lir_list);//���¼�ʱ��
			buttonPlay.setVisible(false);
		}
		//�����һ�װ�ť �¼�
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
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name);//���ò����ļ�
			audioPlay.play();//��ʼ����
			music mc=mylist.get(Index);
			textarea.setText(mc.song_name);//����������
			String[] song_name1=mc.song_name.split("\\.");
			textarea2.setText(song_name1[0]);
			//music mc1=mylist.get(0);
			File file=new File(mc.name); 
			System.out.println(mc.name);
			LinkedList<lirics> lir_list=Liric_process(mc.song_name);
			int iMusicTime;
			 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
            lrc_num = 0;//ÿ���и裬�������¼����
            page=0;//ÿ���и裬���ҳ������¼����
            String rt1=String.format("%02d",mc.minute);
        	String rt2=String.format("%02d",mc.second);
        	String rt="/"+rt1+" : "+rt2;
        	songtime.setText(rt);
			timerFun(iMusicTime,lir_list);//���¼�ʱ��
			buttonPlay.setVisible(false);
		}
		//������ļ���ť �¼�
		 Vector  vt=new Vector ();	//����Vector����ͨ��add������Ӷ���
		if(e.getSource()==buttonPlay3){
			Icon img1=new ImageIcon(".//��Ƭ��2.gif");    
			gif.setVisible(false);
			gif = new JLabel(img1);
			gif.setBounds(380,112,400,270);
			add(gif);	
			 FileDialog openFile=new FileDialog(this,"���ļ�");//�������ļ��Ի���			
			 openFile.setVisible(true);//�Ի���ɼ�
			 String playFileName=openFile.getFile();//��ȡ�򿪵��ļ���
			 if(playFileName.isEmpty())
			 {System.out.println("û��ѡ���ļ�");}
			 //System.out.println(playFileName);
			 String playFileDirectory=openFile.getDirectory();//��ȡ�򿪵��ļ�·��
			 String playFile_path=playFileDirectory+playFileName;//������·��+�ļ���
			 audioPlay.SetPlayAudioPath("file:"+playFile_path);//���ò����ļ�
			 audioPlay.play();//��ʼ����		 
			 
			 textarea.setText(playFileName);//����������
			 System.out.println(playFileName);
			 LinkedList<lirics> lir_list=Liric_process(playFileName);
			 String[] song_name1=playFileName.split("\\.");
			 textarea2.setText(song_name1[0]);
			 File file=new File(playFile_path); 
			 
			 int iMusicTime;
			 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
	         
	         //System.out.println("�ļ�·����"+file.getPath());
	         //System.out.println("�ļ����ƣ�"+file.getName());
	         //System.out.println("���ļ���"+file.isFile());
	         //System.out.println("�ļ����ȣ�"+file.length());
	         //System.out.println("����ʱ�䣺"+iMusicTime);
	         //System.out.println("------------------------------------");
			 lrc_num = 0;//ÿ���и裬�������¼����
	         page=0;//ÿ���и裬���ҳ������¼����
			 timerFun(iMusicTime,lir_list);//���¼�ʱ��
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
				 vt.add(j+"��"+mylist.get(k).song_name+" "+mylist.get(k).minute+":"+mylist.get(k).second);
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
		
		//��������б�ť �¼�����ʾ/��ʧ��
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
		    	Icon imgu2=new ImageIcon(".//�ϲ�1.png");     
				upground = new JLabel(imgu2);
				upground.setBounds(0,0,800,41);
			    getLayeredPane().add(upground, new Integer(Integer.MIN_VALUE+color_layer));
				((JPanel)getContentPane()).setOpaque(false); 
				Icon img_t=new ImageIcon(".//ͼ��2.jpg");    	
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
			    Icon icon_color=new ImageIcon(".//Ƥ��2.jpg");
			    change_color.setIcon(icon_color);	
			    Icon icon_last=new ImageIcon(".//last2.png");
			    buttonPlay2.setIcon(icon_last);	
			    Icon icon_next=new ImageIcon(".//next2.png");
			    buttonPlay1.setIcon(icon_next);	
			    Icon icon_start=new ImageIcon(".//start2.png");
			    buttonPlay.setIcon(icon_start);
			    Icon icon_stop=new ImageIcon(".//stop2.png");
			    buttonStop.setIcon(icon_stop);	
			    textLyrics.setForeground(Color.green);//��ʿؼ�������ɫ
			    textLyrics.setFont(new Font("����",Font.BOLD,15));
			    add(change_color);
		    }
		    else 
		    {
		    	color_layer++;
				color_change=0;
				Icon imgu2=new ImageIcon(".//�ϲ�.png");     
				upground = new JLabel(imgu2);
				upground.setBounds(0,0,800,41);
			    getLayeredPane().add(upground, new Integer(Integer.MIN_VALUE+color_layer));
				((JPanel)getContentPane()).setOpaque(false); 
				Icon img_t=new ImageIcon(".//ͼ��.jpg");    	
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
			    Icon icon_color=new ImageIcon(".//Ƥ��1.jpg");
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
	
	
	    public void mousePressed(MouseEvent e){}//�������
		public void mouseReleased(MouseEvent e){}//�ͷ����
		public void mouseEntered(MouseEvent e){}//������
		public void  mouseExited(MouseEvent e){}//����뿪
		public void  mouseClicked(MouseEvent e){//������		
			// ˫�������б���� �¼�
			 if (e.getClickCount() == 2) {//�����������������
				 if(e.getSource()==listPlayFile){//����¼�Դ�ǲ����б����ڲ����б�ؼ���˫������ִ�С�
				 //���˫�������б��еĴ��룬�����ȡ�����������Ҳ��š�
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
					 textarea.setText(str);//����������
					 String[] song_name1=str.split("\\.");
					 textarea2.setText(song_name1[0]);
					 audioPlay.SetPlayAudioPath("file:"+E.name);//���ò����ļ�
					 System.out.println(E.name);
					 audioPlay.play();//��ʼ����
					 
					 File file=new File(mylist.get(index).name); 
					 System.out.println(file.getName());

			         System.out.println(str);
			         LinkedList<lirics> lir_list=Liric_process(str);
			         int iMusicTime;
					 //������ʦ�����ļ����Զ����ļ�(�����ʲ�ͬ ����)
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
					 lrc_num = 0;//ÿ���и裬�������¼����
			         page=0;//ÿ���и裬���ҳ������¼����
			         String rt1=String.format("%02d",E.minute);
			        	String rt2=String.format("%02d",E.second);
			        	String rt="/"+rt1+" : "+rt2;
			        	songtime.setText(rt);
					 timerFun(iMusicTime,lir_list);//���¼�ʱ��
					 buttonPlay.setVisible(false);
				 } 
			 }	
	    }
}


//������
public class musicplayer{
@SuppressWarnings("unchecked")//���Ծ���
    	public static void main(String[] args) { 
        MyExtendsJFrame frame=new MyExtendsJFrame();//����������򴰿�  
        frame.setUndecorated(true);
        frame.setVisible(true);//��������������ִ��
    }
}
