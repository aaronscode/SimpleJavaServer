public class HttpRequest
{
  // three parts of any http request
  private String initalRequestLine; // of form METHOD RESOURCE_LOCATION HTTP_VERSION
  private String[] headers; // should never be empty
  private String[] body = null; // may be empty
  
  private boolean validRequest; // does req have valid inital request line?

  private String method, resLocation, httpVersion; // three parts of the inital request line
  private String rewrittenResLocation; // re-written inital loc to account for any rewrite rules


  public HttpRequest(String irq, String[] h, String[] b)
  {
    this.initalRequestLine = irq;
    this.headers = h;
    this.body = b;

    try
    {
      String[] irqSplit = irq.split(" ");
      this.method = irqSplit[0]; // TODO validate method
      this.resLocation = irqSplit[1];
      this.httpVersion = irqSplit[2];
      this.rewrittenResLocation = rewriteResLoc(this.resLocation);

      this.validRequest = true;
    }
    catch(Exception e)
    {
      System.err.println("ERROR PARSING INITIAL REQUEST LINE");
      System.err.println(e);
      this.validRequest = false;
    }
  }

  // implement a standard apache rewrite rule where / is turned into /index.html
  private String rewriteResLoc(String resourceLocation)
  {
    if(resourceLocation.charAt(resourceLocation.length() - 1) == '/')
     return resourceLocation + "index.html";
    else
      return resourceLocation;
  }

  public boolean isValid()
  {
    return this.validRequest;
  }

  public String method()
  {
    return this.method;
  }

  public String resLocation()
  {
    return this.resLocation;
  }

  public String rewrittenResLocation()
  {
    return this.rewrittenResLocation;
  }

  public String httpVersion()
  {
    return this.httpVersion;
  }

  public String toString()
  {
    String retval = this.initalRequestLine + "\n";
    for(int i = 0; i < this.headers.length; i++)
    {
      retval += this.headers[i] + "\n";
    }

    retval += "\n";
    
    for(int i = 0; i < this.body.length; i++)
    {
      retval += this.body[i] + "\n";
    }
    
    return retval;
  }
}
