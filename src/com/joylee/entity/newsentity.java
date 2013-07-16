package com.joylee.entity;


public class newsentity {
	
	private String id;
	private String url;
	private String title;
	private String detail;
    private String anthor;
    private String newsdatetime;
    private String newsimage;
    private String newsid;
    private String source;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail() {
		return detail;
	}
	public void  setDetail(String detail) {
		this.detail = detail;
	}

    public String getNewsDatetime()
    {
        return newsdatetime;
    }

    public  void setNewsDatetime(String newsdatetime)
    {
        this.newsdatetime=newsdatetime;
    }


    public String getAnthor()
    {
        return anthor;
    }

    public  void setAnthor(String anthor)
    {
        this.anthor=anthor;
    }

    public String getNewsimage()
    {
        return newsimage;
    }

    public  void setNewsimage(String newsimage)
    {
        this.newsimage=newsimage;
    }


    public String getNewsid()
    {
        return newsid;
    }

    public  void setNewsid(String newsid)
    {
        this.newsid=newsid;
    }

    public String getSource()
    {
        return source;
    }

    public  void setSource(String source)
    {
        this.source=source;
    }

}
