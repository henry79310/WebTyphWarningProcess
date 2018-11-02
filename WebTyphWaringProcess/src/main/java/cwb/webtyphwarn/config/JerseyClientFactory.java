package cwb.webtyphwarn.config;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class JerseyClientFactory {
	
//	private static final int HTTP_MAX_CONNECTION = 200;
//	private static final int DEFAULT_MAX_PEROUTE = 20;
//	private static int CONNECT_TIMEOUT = 5000;
//	private static int READ_TIMEOUT = 5000;
//	private static int POOLCM_SOCKET_TIMEOUT = 3000;
	
	public static Client create() {
		try {
			
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] {
					new X509TrustManager() {
						
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						
						@Override
						public void checkClientTrusted(X509Certificate[] arg0, String arg1)
								throws CertificateException {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void checkServerTrusted(X509Certificate[] arg0, String arg1)
								throws CertificateException {
							// TODO Auto-generated method stub
							
						}
			}}, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			
			
			//ClientConfig clientConfig = new ClientConfig();
			//clientConfig.property(ClientProperties.CONNECT_TIMEOUT,  CONNECT_TIMEOUT);
			//clientConfig.property(ClientProperties.READ_TIMEOUT, READ_TIMEOUT);
			//clientConfig.property(ClientProperties.REQUEST_ENTITY_PROCESSING, RequestEntityProcessing.BUFFERED);
			
			//PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
			
			//pcm.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(POOLCM_SOCKET_TIMEOUT).build());
			//pcm.setMaxTotal(HTTP_MAX_CONNECTION);
			//pcm.setDefaultMaxPerRoute(DEFAULT_MAX_PEROUTE);
			
			//clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, pcm);
			
			//clientConfig.connectorProvider(new ApacheConnectorProvider());
			//clientConfig.register(JacksonFeature.class);
	
			Client client = ClientBuilder.newBuilder()
					//.withConfig(clientConfig)
					.sslContext(sslContext)
					.hostnameVerifier(new HostnameVerifier() {
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							// TODO Auto-generated method stub
							return true;
						}
					})
					.build();
			
			return client;
			
		}catch(Exception e) {
			return null;
		}
		
		
	}
	
	
	
}
