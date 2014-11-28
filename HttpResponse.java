import java.text.SimpleDateFormat;
import java.util.*;

public class HttpResponse
{
	private final String HTTP_VERSION = "HTTP/1.1";
	private String statusLine;

	private boolean hasBody;

	public HttpResponse(HttpRequest req)
	{
		// try to make a file from req.
	}

	public HttpResponse(int statusCode)
	{
		this.setStatusLine(statusCode);
	}

	public String[] getResponse()
	{
    String[] response = new String[5];
    response[0] = "HTTP/1.1 200 OK\n";
    response[1] = "Date: " + getServerTime() + "\n";
    response[2] = "Connection: close\n";
    response[3] = "\n";
    response[4] = "<html><head><title>An Example Page</title></head><body>Hello World, this is a very simple HTML document.</body></html>\n";

    return response;
	}

	public void setStatusLine(int statusCode)
	{
		this.statusLine = HTTP_VERSION + statusCode + this.getEnglishStatus(statusCode);
	}

	public void setStatusLine(String statusLine)
	{
		this.statusLine = statusLine;
	}

	private String getEnglishStatus(int statusCode)
	{
		switch(statusCode)
		{
			case 200:
				return "OK";
			case 404:
				return "Not Found";
			case 500:
				return "Server Error";
			default:
				return "Unknown Status";
		}
	}

  private String getServerTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
  }

}
