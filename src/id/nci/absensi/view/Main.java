package id.nci.absensi.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.eclipse.jetty.util.component.LifeCycle;

import id.nci.absensi.controller.libraries.RFIDReader;
import id.nci.absensi.view.WebsocketClientEndpoint;
import id.nci.absensi.controller.apiserver.ApiMain;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.JCheckBox;

//websocket
import java.net.URI;
import java.net.URISyntaxException;
//endwebsocket
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;


public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel panel_footer = new JPanel();
	private static JTextField tfIp_address,tfIp_SubnetMask,tfIp_Gateway,tfDbm,tfStatus;
	private static JTextField tfIp_address2,tfIp_SubnetMask2,tfIp_Gateway2,tfDbm2,tfStatus2;
	private static JTextField tfIp_address3,tfIp_SubnetMask3,tfIp_Gateway3,tfDbm3,tfStatus3;
	private static JTextField tfIp_address4,tfIp_SubnetMask4,tfIp_Gateway4,tfDbm4,tfStatus4;
	private static JTextField tfIp_address5,tfIp_SubnetMask5,tfIp_Gateway5,tfDbm5,tfStatus5;

	private static String ipAddress,IpSubnetMask,IpGateway;
	private static String ipAddress2,IpSubnetMask2,IpGateway2;
	private static int dBm,dBm2;
	private static TextArea textArea;
	private static JButton btnOn,btnOn2,btnOn3,btnOn4,btnOn5;
	private static JButton btnOff,btnOff2,btnOff3,btnOff4,btnOff5;
	private static JCheckBox cbYa,cbYa2,cbYa3,cbYa4,cbYa5;
	
	private static int port = 100;
	private static RFIDReader reader_ip200 = null;
	private static RFIDReader reader_ip201 = null;
	
	private static boolean cekInsert = false; 
	//Websocket	
	private static String BtnNyala = null;
	
	private static String id = null; 

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Antena Control Absensi RFID");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 693, 435);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(10, 11, 108, 14);
		panel.add(lblIpAddress);
		
		tfIp_address = new JTextField();
		tfIp_address.setText("192.168.0.200");
		tfIp_address.setBounds(10, 26, 108, 22);
		panel.add(tfIp_address);
		tfIp_address.setColumns(10);
		
		tfIp_address2 = new JTextField();
		tfIp_address2.setText("192.168.0.201");
		tfIp_address2.setColumns(10);
		tfIp_address2.setBounds(10, 56, 108, 22);
		panel.add(tfIp_address2);
		
		tfIp_address3 = new JTextField();
		tfIp_address3.setText("192.168.0.202");
		tfIp_address3.setColumns(10);
		tfIp_address3.setBounds(10, 86, 108, 22);
		panel.add(tfIp_address3);
		
		tfIp_address4 = new JTextField();
		tfIp_address4.setText("192.168.0.203");
		tfIp_address4.setColumns(10);
		tfIp_address4.setBounds(10, 116, 108, 22);
		panel.add(tfIp_address4);
		
		tfIp_address5 = new JTextField();
		tfIp_address5.setText("192.168.0.204");
		tfIp_address5.setColumns(10);
		tfIp_address5.setBounds(10, 146, 108, 22);
		panel.add(tfIp_address5);
		
		JLabel lblIpSubnetMask = new JLabel("Subnet Mask");
		lblIpSubnetMask.setBounds(128, 11, 110, 14);
		panel.add(lblIpSubnetMask);
		
		tfIp_SubnetMask = new JTextField();
		tfIp_SubnetMask.setText("255.255.255.0");
		tfIp_SubnetMask.setBounds(128, 26, 110, 22);
		panel.add(tfIp_SubnetMask);
		tfIp_SubnetMask.setColumns(10);
		
		tfIp_SubnetMask2 = new JTextField();
		tfIp_SubnetMask2.setText("255.255.255.0");
		tfIp_SubnetMask2.setColumns(10);
		tfIp_SubnetMask2.setBounds(128, 56, 110, 22);
		panel.add(tfIp_SubnetMask2);
		
		tfIp_SubnetMask3 = new JTextField();
		tfIp_SubnetMask3.setText("255.255.255.0");
		tfIp_SubnetMask3.setColumns(10);
		tfIp_SubnetMask3.setBounds(128, 86, 110, 22);
		panel.add(tfIp_SubnetMask3);
		
		tfIp_SubnetMask4 = new JTextField();
		tfIp_SubnetMask4.setText("255.255.255.0");
		tfIp_SubnetMask4.setColumns(10);
		tfIp_SubnetMask4.setBounds(128, 116, 110, 22);
		panel.add(tfIp_SubnetMask4);
		
		tfIp_SubnetMask5 = new JTextField();
		tfIp_SubnetMask5.setText("255.255.255.0");
		tfIp_SubnetMask5.setColumns(10);
		tfIp_SubnetMask5.setBounds(128, 146, 110, 22);
		panel.add(tfIp_SubnetMask5);
		
		JLabel lblDefaultGateway = new JLabel("Default Gateway");
		lblDefaultGateway.setBounds(248, 11, 108, 14);
		panel.add(lblDefaultGateway);
		
		tfIp_Gateway = new JTextField();
		tfIp_Gateway.setText("192.168.0.100");
		tfIp_Gateway.setColumns(10);
		tfIp_Gateway.setBounds(248, 26, 108, 22);
		panel.add(tfIp_Gateway);
		
		tfIp_Gateway2 = new JTextField();
		tfIp_Gateway2.setText("192.168.0.100");
		tfIp_Gateway2.setColumns(10);
		tfIp_Gateway2.setBounds(248, 56, 108, 22);
		panel.add(tfIp_Gateway2);
		
		tfIp_Gateway3 = new JTextField();
		tfIp_Gateway3.setText("192.168.0.100");
		tfIp_Gateway3.setColumns(10);
		tfIp_Gateway3.setBounds(248, 86, 108, 22);
		panel.add(tfIp_Gateway3);
		
		tfIp_Gateway4 = new JTextField();
		tfIp_Gateway4.setText("192.168.0.100");
		tfIp_Gateway4.setColumns(10);
		tfIp_Gateway4.setBounds(248, 116, 108, 22);
		panel.add(tfIp_Gateway4);
		
		tfIp_Gateway5 = new JTextField();
		tfIp_Gateway5.setText("192.168.0.100");
		tfIp_Gateway5.setColumns(10);
		tfIp_Gateway5.setBounds(248, 146, 108, 22);
		panel.add(tfIp_Gateway5);
		
		JLabel lblSensitifitas = new JLabel("dBm");
		lblSensitifitas.setBounds(366, 11, 33, 14);
		panel.add(lblSensitifitas);
		
		tfDbm = new JTextField();
		tfDbm.setText("0");
		tfDbm.setColumns(10);
		tfDbm.setBounds(366, 27, 33, 20);		
		panel.add(tfDbm);
		
		tfDbm2 = new JTextField();
		tfDbm2.setText("0");
		tfDbm2.setColumns(10);
		tfDbm2.setBounds(366, 57, 33, 20);
		panel.add(tfDbm2);
		
		tfDbm3 = new JTextField();
		tfDbm3.setText("0");
		tfDbm3.setColumns(10);
		tfDbm3.setBounds(366, 87, 33, 20);
		panel.add(tfDbm3);
		
		tfDbm4 = new JTextField();
		tfDbm4.setText("0");
		tfDbm4.setColumns(10);
		tfDbm4.setBounds(366, 117, 33, 20);
		panel.add(tfDbm4);
		
		tfDbm5 = new JTextField();
		tfDbm5.setText("0");
		tfDbm5.setColumns(10);
		tfDbm5.setBounds(366, 147, 33, 20);
		panel.add(tfDbm5);
		
		JLabel lblReset = new JLabel("Reset");
		lblReset.setBounds(409, 11, 40, 14);
		panel.add(lblReset);
		
		cbYa = new JCheckBox("Ya");
		cbYa.setSelected(false);
		cbYa.setBounds(405, 26, 46, 23);
		panel.add(cbYa);
		
		cbYa2 = new JCheckBox("Ya");
		cbYa2.setSelected(false);
		cbYa2.setBounds(405, 56, 46, 23);
		panel.add(cbYa2);
		
		cbYa3 = new JCheckBox("Ya");
		cbYa3.setSelected(false);
		cbYa3.setBounds(405, 86, 46, 23);
		panel.add(cbYa3);
		
		cbYa4 = new JCheckBox("Ya");
		cbYa4.setSelected(false);
		cbYa4.setBounds(405, 116, 46, 23);
		panel.add(cbYa4);
		
		cbYa5 = new JCheckBox("Ya");
		cbYa5.setSelected(false);
		cbYa5.setBounds(405, 146, 46, 23);
		panel.add(cbYa5);
		
		JLabel lblAksi = new JLabel("Lakukan Aksi Disini");
		lblAksi.setBounds(451, 11, 118, 14);
		panel.add(lblAksi);
		
		btnOn = new JButton("On");
		btnOn.setBounds(451, 25, 58, 23);
		btnOn.addActionListener(new ActionBtnNyalakan());
		panel.add(btnOn);
		btnOff = new JButton("Off");
		btnOff.setBounds(511, 25, 58, 23);
		btnOff.addActionListener(new ActionBtnMatikan());
		panel.add(btnOff);
		
		btnOn2 = new JButton("On");
		btnOn2.setBounds(451, 55, 58, 23);
		btnOn2.addActionListener(new ActionBtnNyalakan2());
		panel.add(btnOn2);
		btnOff2 = new JButton("Off");
		btnOff2.setBounds(511, 55, 58, 23);
		btnOff2.addActionListener(new ActionBtnMatikan2());
		panel.add(btnOff2);
		
		btnOn3 = new JButton("On");
		btnOn3.setBounds(451, 85, 58, 23);
		panel.add(btnOn3);
		btnOff3 = new JButton("Off");
		btnOff3.setBounds(511, 85, 58, 23);
		panel.add(btnOff3);
		
		btnOn4 = new JButton("On");
		btnOn4.setBounds(451, 115, 58, 23);
		panel.add(btnOn4);
		btnOff4 = new JButton("Off");
		btnOff4.setBounds(511, 115, 58, 23);
		panel.add(btnOff4);
		
		btnOn5 = new JButton("On");
		btnOn5.setBounds(451, 145, 58, 23);
		panel.add(btnOn5);
		btnOff5 = new JButton("Off");
		btnOff5.setBounds(511, 145, 58, 23);
		panel.add(btnOff5);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(581, 11, 46, 14);
		panel.add(lblStatus);
		
		tfStatus = new JTextField();
		tfStatus.setEditable(false);
		tfStatus.setColumns(10);
		tfStatus.setBounds(579, 26, 86, 22);
		panel.add(tfStatus);
		
		tfStatus2 = new JTextField();
		tfStatus2.setEditable(false);
		tfStatus2.setColumns(10);
		tfStatus2.setBounds(579, 56, 86, 22);
		panel.add(tfStatus2);
		
		tfStatus3 = new JTextField();
		tfStatus3.setEditable(false);
		tfStatus3.setColumns(10);
		tfStatus3.setBounds(579, 86, 86, 22);
		panel.add(tfStatus3);
		
		tfStatus4 = new JTextField();
		tfStatus4.setEditable(false);
		tfStatus4.setColumns(10);
		tfStatus4.setBounds(579, 116, 86, 22);
		panel.add(tfStatus4);
		
		tfStatus5 = new JTextField();
		tfStatus5.setEditable(false);
		tfStatus5.setColumns(10);
		tfStatus5.setBounds(579, 146, 86, 22);
		panel.add(tfStatus5);
		
		JLabel lblDataLog = new JLabel("Data Log");
		lblDataLog.setBounds(10, 191, 572, 14);
		panel.add(lblDataLog);
		
		textArea = new TextArea();
		textArea.setBounds(10, 208, 655, 145);
		panel.add(textArea);
		
		panel_footer.setBackground(Color.WHITE);
		panel_footer.setBounds(0, 365, 677, 31);
		panel.add(panel_footer);
		panel_footer.setLayout(null);
		
		JLabel lblCopyrightPtnuansaCerah = new JLabel("Copyright \u00A9 2020 PT.Nuansa Cerah Informasi");
		lblCopyrightPtnuansaCerah.setBounds(397, 11, 259, 14);
		panel_footer.add(lblCopyrightPtnuansaCerah);
	}
	
	static class ActionBtnNyalakan implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				ipAddress = tfIp_address.getText().toString();
				IpSubnetMask = tfIp_SubnetMask.getText().toString();
				IpGateway = tfIp_Gateway.getText().toString();
				String StrdBm = tfDbm.getText().toString();
				dBm = Integer.parseInt(StrdBm.trim());
				
				textArea.append("\n");
				textArea.append("====== Cek Koneksi Internet Antena 1 ======\n");
				textArea.append("mohon tunggu...\n");
				textArea.append("sedang cek koneksi...\n");
				
				Boolean koneksi = cek_koneksi();
//				if(koneksi == true) {
					textArea.append("koneksi berhasil...\n");
					textArea.append("\n");
					textArea.append("====== Antena 01 Sedang Diaktifkan ======\n");
					textArea.append("mohon tunggu...\n");
					reader_ip200 = new RFIDReader(ipAddress,port,"Antena01");
					reader_ip200.activeRF();
					reader_ip200.setSensitive(dBm);
					reader_ip200.setIP(ipAddress,port,IpSubnetMask,IpGateway);
					if (cbYa.isSelected()) {
						textArea.append("antena 01 berhasil diaktifkan...\n");
						reader_ip200.reset();
						textArea.append("antena direset...\n");
						textArea.append("reset berhasil...\n");
						tfStatus.setText("direset");
					}else {
						tfStatus.setText("aktif");
						
						//give value to variable to run websocket
						BtnNyala = "true";
						runAntena();
					}
					
					btnOn.setEnabled(false);
					btnOff.setEnabled(true);
//				}else {
//					textArea.append("koneksi gagal...\n");
//					btnOn.setEnabled(true);
//					btnOff.setEnabled(false);
//					tfStatus.setText("koneksi gagal");
//				}
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	static class ActionBtnNyalakan2 implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				ipAddress2 = tfIp_address2.getText().toString();
				IpSubnetMask2 = tfIp_SubnetMask2.getText().toString();
				IpGateway2 = tfIp_Gateway2.getText().toString();
				String StrdBm = tfDbm2.getText().toString();
				dBm2 = Integer.parseInt(StrdBm.trim());
				
				textArea.append("\n");
				textArea.append("====== Cek Koneksi Internet Antena 2 ======\n");
				textArea.append("mohon tunggu...\n");
				textArea.append("sedang cek koneksi...\n");
				
//				Boolean koneksi = cek_koneksi();
//				if(koneksi == true) {
					textArea.append("koneksi berhasil...\n");
					textArea.append("\n");
					textArea.append("====== Antena 02 Sedang Diaktifkan ======\n");
					textArea.append("mohon tunggu...\n");
					reader_ip201 = new RFIDReader(ipAddress2,port,"Antena02");
					reader_ip201.activeRF();
					reader_ip201.setSensitive(dBm2);
					reader_ip201.setIP(ipAddress2,port,IpSubnetMask2,IpGateway2);
					if (cbYa2.isSelected()) {
						textArea.append("antena 02 berhasil diaktifkan...\n");
						reader_ip201.reset();
						textArea.append("antena direset...\n");
						textArea.append("reset berhasil...\n");
						tfStatus2.setText("direset");
					}else {
//						textArea.append("antena 02 berhasil diaktifkan...\n");
						tfStatus2.setText("aktif");
						
						//give value to variable to run websocket
						BtnNyala = "true";
						
						runAntena2();
					}
					
					btnOn2.setEnabled(false);
					btnOff2.setEnabled(true);
//				}else {
//					textArea.append("koneksi gagal...\n");
//					btnOn2.setEnabled(true);
//					btnOff2.setEnabled(false);
//					tfStatus2.setText("koneksi gagal");
//				}
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	static class ActionBtnMatikan implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				textArea.append("\n");
				textArea.append("====== Antena 01 Sedang Dimatikan ======\n");
				textArea.append("mohon tunggu...\n");
				
				//give variable to false to stop push notif to websocket
				BtnNyala = "false";
				
				Boolean koneksi = cek_koneksi();
//				if(koneksi == true) {
					btnOff.setEnabled(false);
					btnOn.setEnabled(true);
					reader_ip200.closeRF();
					reader_ip200.disconnect();
					textArea.append("Antena 01 berhasil dimatikan...\n");
					tfStatus.setText("mati");
//				}else {
//					tfStatus.setText("koneksi gagal");
//					textArea.append("koneksi gagal...\n");
//					textArea.append("mohon periksa internet anda...\n");
//				}
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	static class ActionBtnMatikan2 implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				textArea.append("\n");
				textArea.append("====== Antena 02 Sedang Dimatikan ======\n");
				textArea.append("mohon tunggu...\n");
				
				//give variable to false to stop push notif to websocket
				BtnNyala = "false";
				
				Boolean koneksi = cek_koneksi();
//				if(koneksi == true) {
					btnOff2.setEnabled(false);
					btnOn2.setEnabled(true);
					reader_ip201.closeRF();
					reader_ip201.disconnect();
					textArea.append("Antena 02 berhasil dimatikan...\n");
					tfStatus2.setText("mati");
//				}else {
//					tfStatus2.setText("koneksi gagal");
//					textArea.append("koneksi gagal...\n");
//					textArea.append("mohon periksa internet anda...\n");
//				}
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private static void runAntena() {
		textArea.append("antena 01 berhasil diaktifkan...\n");
		Thread run_antena = new Thread(new Runnable(){
			@SuppressWarnings("unchecked")
			@Override
			public void run(){
				
				// TODO Auto-generated method stub
				while(reader_ip200.isActive()==true){
//					Boolean koneksi = cek_koneksi();
//					if(koneksi == true) {
						List<HashMap<String,String>> list;
						list = (ArrayList<HashMap<String,String>>)reader_ip200.read().getData();
//						System.out.println(list);
						
						textArea.append("\n");
						textArea.append("====== TAG ID Terdeteksi ======\n");
						textArea.append("tunggu...\n");
						
						for(int i=0,iLen = list.size();i<iLen;i++){
							String id = list.get(i).get("ID");
							
							try {
								String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
								String timeStampFormat = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
								String gate = "1";
								
								//jika button RFID dinyalakan
								if(BtnNyala == "true") {
									cekInsert = ApiMain.POST_tags_baru(id,gate,timeStamp);
//									System.out.println("status button RFID: "+BtnNyala);
//									System.out.println("status button RFID cekInsert: "+cekInsert);
									//push notif to websocket
									if(cekInsert == true) {
//										WebSocketSend(id,gate,timeStampFormat);
										
									}else {
										textArea.append("Tag tidak terdaftar : "+id+"\n");
									}

								}
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println(e);
							}
						}
//					}else {
//						textArea.append("\n");
//						textArea.append("koneksi gagal...\n");
//						textArea.append("mohon periksa internet anda...\n");
//					}
					
					try{
						Thread.sleep(8000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		});
		run_antena.start();
	}
	
	private static void runAntena2() {
		textArea.append("antena 02 berhasil diaktifkan...\n");
		Thread run_antena = new Thread(new Runnable(){
			@SuppressWarnings("unchecked")
			@Override
			public void run(){
				
				// TODO Auto-generated method stub
				while(reader_ip201.isActive()==true){
					textArea.append("\n");
					textArea.append("====== TAG ID Terdeteksi ======\n");
					textArea.append("tunggu...\n");
					
//					while(reader_ip201.isActive()==true){
//					Boolean koneksi = cek_koneksi();
//					if(koneksi == true) {
						List<HashMap<String,String>> list;
						list = (ArrayList<HashMap<String,String>>)reader_ip201.read().getData();

						
						System.out.println("Total : "+list.size());
						for(int i=0,iLen = list.size();i<iLen;i++){

							id = list.get(i).get("ID");
							textArea.append("Tag terdeteksi : "+id+"\n");

							try {
////								System.out.println("ID"+id);
								String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
								String timeStampFormat = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
								String gate = "2";
//								System.out.println("button : "+BtnNyala);
//
//								
//								//jika button RFID dinyalakan
								if(BtnNyala == "true") {
//									System.out.println("button2 : "+BtnNyala);
									cekInsert = ApiMain.POST_tags_baru(id,gate,timeStamp);
									System.out.println("status button RFID: "+BtnNyala);
									System.out.println("status button RFID cekInsert: "+cekInsert);
//									//push notif to websocket
//									if(cekInsert == true) {
//										textArea.append("Tag sukses direkam : "+id+"\n");
////										WebSocketSend(id,gate,timeStampFormat);
//
//										
//									}else {
//										textArea.append("Tag tidak terdaftar : "+id+"\n");
//									}
////
								}
//								
								Thread.sleep(1000);
//
//								
							} catch (IOException e) {
////								System.out.println("main thread interupted");
////								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println(e);
							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
							}
							System.out.println("main thread exiting");
							
							
						}
						
//						System.out.println("ID : "+id);
//					}else {
//						textArea.append("\n");
//						textArea.append("koneksi gagal...\n");
//						textArea.append("mohon periksa internet anda...\n");
//					}
					
//					try{
//						Thread.sleep(8000);
//					}catch(InterruptedException e){
//						e.printStackTrace();
//					}
				}
			}
		});
		run_antena.start();
	}
	
	private static boolean cek_koneksi() {
		boolean connected = false;
		try {
			URL url = new URL("https://test-absensi.rfidtotalsolution.com");
	        URLConnection connection = url.openConnection();
	        connection.connect();
	        connected = true;
		}catch(Exception e) {
			connected = false;
		}
		return connected;
	}
	
	private static void WebSocketSend(String id, String gate, String timeStamp) {
		try {
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://127.0.0.1:5000"));
			// add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println("pesan : "+message);
                }
            });
            
            
            // send message to websocket
            //clientEndPoint.sendMessage("{'id':'"+id+"','gate':'"+gate+"','time':'"+timeStamp+"'}");
            clientEndPoint.sendMessage(id+","+gate+","+timeStamp);
            // wait 5 seconds for messages from websocket
            Thread.sleep(8000);
            
            
//            Thread.currentThread().suspend();
//            continue;
		} catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
	}
}
