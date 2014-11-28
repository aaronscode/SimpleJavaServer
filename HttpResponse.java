public class HttpResponse
{
	private PrintWriter out;

	private final String HTTP_VERSION = "HTTP/1.1";
	private String statusLine;

	private boolean hasBody;

	public HttpResponse(HttpRequest req, PrintWriter out)
	{
		this.out = out;
		
		// try to make a file from req.
	}

	public HttpResponse(int statusCode, PrintWriter out)
	{
		this.setStatusLine(statusCode);
	}

	public void respond()
	{
		// print statusline
		
		/*
		 * for(int i = 0; i < headers.length; i++)
		 * {
		 *	// print headers
		 * }
		 */

		// output raw bytestream
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
				break;
			case 404:
				return "Not Found";
				break;
			case 500:
				return "Server Error";
				break;
			default:
				return "Unknown Status";
				break;
		}
	}
}
