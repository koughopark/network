package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler extends Thread {
   private static final String DOCUMENT_ROOT = "./webapp";

   private Socket socket;

   public RequestHandler(Socket socket) {
      this.socket = socket;
   }

   @Override
   public void run() {
      try {
         // logging Remote Host IP Address & Port
         InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
         consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
               + inetSocketAddress.getPort());         

         // get IOStream
         BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
         OutputStream os = socket.getOutputStream();
         
         String request = null;
         while( true ){
            String line = br.readLine();
            if( line == null || "".equals(line)){
               break;
               
            }
            if(request == null){
               request = line;
               break;
            }
         
         }         
         
         consoleLog(request);
         String[] tokens = request.split( " " );
         responseStaticResource(os, tokens[1], tokens[2]);

         
         // sdfasfasfsadfsafsadfa
         File file = new File( "./webapp" + tokens[1] );
         Path path = file.toPath();
         byte[] body = Files.readAllBytes( path );
         System.out.println(body);
         
         // 예제 응답입니다.
         // 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
         os.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" )); 
         os.write( "Content-Type:text/html\r\n".getBytes( "UTF-8" ) );
         os.write( "\r\n".getBytes() );
         os.write( body );         
         
      } catch ( Exception ex ) {
         consoleLog( "error:" + ex );
      } finally {
         // clean-up
         try {
            if ( socket != null && socket.isClosed() == false ) {
               socket.close();
            }
         } catch ( IOException ex)  {
            consoleLog( "error:" + ex );
         }
      }
   }

   private void consoleLog(String message) {
      System.out.println("[RequestHandler#" + getId() + "] " + message);
   }
   
   private void responseStaticResource( OutputStream outputStream, String url, String protocol )throws IOException {
	    try {
//			throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }   
}