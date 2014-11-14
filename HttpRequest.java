public class HttpRequest
{
  // three parts of any http request
  private String initalRequestLine; // of form METHOD RESOURCE_LOCATION HTTP_VERSION
  private String[] headers; // should never be empty
  private String[] body = null; // may be empty

  private String method, resLocation, httpVersion; // three parts of the inital request line


  public HttpRequest(String irq, String[] h, String[] b)
  {
    this.initalRequestLine = irq;
    this.headers = h;
    this.body = b;

    // TODO better error checking here
    String[] irqSplit = irq.split(" ");
    try
    {
      this.method = irqSplit[0];
      this.resLocation = irqSplit[1];
      this.httpVersion = irqSplit[2];
    }
    catch(Exception e)
    {

    }
  }

  public String method()
  {
    return this.method;
  }

  public String resLocation()
  {
    return this.resLocation;
  }

  public String httpVersion()
  {
    return this.httpVersion;
  }

  public String toString()
  {
    String retval = this.initalRequestLine;
    for(int i = 0; i < this.headers.length(); i++)
    {
      retval += this.headers[i] + "\n";
    }

    retval += "\n";
    
    for(int i = 0; i < this.body.length(); i++)
    {
      retval += this.body[i] + "\n";
    }
    
    return retval;
  }
}
