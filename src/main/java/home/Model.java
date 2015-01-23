package home;

public class Model {

	String date;
	String time;
	String time_taken;
	String x_cs_dns;
	String sc_status;
	String s_action;
	String sc_bytes;
	String cs_bytes;
	String cs_method;
	String cs_uri_scheme;
	String cs_host;
	String cs_uri_path;;
	String cs_uri_query;
	String del2;
	public String cs_uri_port;
	String cs_username;
	String cs_auth_group;
	String s_hierarchy;
	String s_supplier_name;
	String rs_Content_Type;
	String cs_User_Agent = "";
	String sc_filter_result = "";
	String cs_categories ="";
	String  cs_uri_extension = "";
	String c_ip = "";
	String s_ip = "";
	String x_exception_id="";
	
	
	public Model(String[] data) {
		super();
		 
		this.date = data[0];
		this.time = data[1];
		this.time_taken = data[2];
		this.x_cs_dns = data[3];
		this.sc_status = data[4];
		this.s_action = data[5];
		this.sc_bytes = data[6];
		this.cs_bytes = data[7];
		this.cs_method = data[8];
		this.cs_uri_scheme = data[9];
		this.cs_host = data[10];
		this.cs_uri_path = data[11];
		this.cs_uri_query = data[12];
		//this.del2 = data[13];
		this.cs_uri_port = data[13];
		this.cs_username=data[14];
		this.cs_auth_group=data[15];		
		this.s_hierarchy = data[16];
		this.s_supplier_name = data[17];
		this.rs_Content_Type = data[18];
		this.cs_User_Agent= data[19];
		this.sc_filter_result = data[20];
		this.cs_categories = data[21];
		this.cs_uri_extension = data[22];
		this.c_ip = data[23];
		this.s_ip = data[24];
		this.x_exception_id = data[25];
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime_taken() {
		return time_taken;
	}

	public void setTime_taken(String time_taken) {
		this.time_taken = time_taken;
	}

	public String getX_cs_dns() {
		return x_cs_dns;
	}

	public void setX_cs_dns(String x_cs_dns) {
		this.x_cs_dns = x_cs_dns;
	}

	public String getSc_status() {
		return sc_status;
	}

	public void setSc_status(String sc_status) {
		this.sc_status = sc_status;
	}

	public String getS_action() {
		return s_action;
	}

	public void setS_action(String s_action) {
		this.s_action = s_action;
	}

	public String getSc_bytes() {
		return sc_bytes;
	}

	public void setSc_bytes(String sc_bytes) {
		this.sc_bytes = sc_bytes;
	}

	public String getCs_bytes() {
		return cs_bytes;
	}

	public void setCs_bytes(String cs_bytes) {
		this.cs_bytes = cs_bytes;
	}

	public String getCs_method() {
		return cs_method;
	}

	public void setCs_method(String cs_method) {
		this.cs_method = cs_method;
	}

	public String getCs_uri_scheme() {
		return cs_uri_scheme;
	}

	public void setCs_uri_scheme(String cs_uri_scheme) {
		this.cs_uri_scheme = cs_uri_scheme;
	}

	public String getCs_host() {
		return cs_host;
	}

	public void setCs_host(String cs_host) {
		this.cs_host = cs_host;
	}

	public String getCs_uri_path() {
		return cs_uri_path;
	}

	public void setCs_uri_path(String cs_uri_path) {
		this.cs_uri_path = cs_uri_path;
	}

	public String getCs_uri_query() {
		return cs_uri_query;
	}

	public void setCs_uri_query(String cs_uri_query) {
		this.cs_uri_query = cs_uri_query;
	}

	public String getDel2() {
		return del2;
	}

	public void setDel2(String del2) {
		this.del2 = del2;
	}

	public String getCs_uri_port() {
		return cs_uri_port;
	}

	public void setCs_uri_port(String cs_uri_port) {
		this.cs_uri_port = cs_uri_port;
	}

	public String getCs_username() {
		return cs_username;
	}

	public void setCs_username(String cs_username) {
		this.cs_username = cs_username;
	}

	public String getCs_auth_group() {
		return cs_auth_group;
	}

	public void setCs_auth_group(String cs_auth_group) {
		this.cs_auth_group = cs_auth_group;
	}

	public String getS_hierarchy() {
		return s_hierarchy;
	}

	public void setS_hierarchy(String s_hierarchy) {
		this.s_hierarchy = s_hierarchy;
	}

	public String getS_supplier_name() {
		return s_supplier_name;
	}

	public void setS_supplier_name(String s_supplier_name) {
		this.s_supplier_name = s_supplier_name;
	}

	public String getRs_Content_Type() {
		return rs_Content_Type;
	}

	public void setRs_Content_Type(String rs_Content_Type) {
		this.rs_Content_Type = rs_Content_Type;
	}

	public String getCs_User_Agent() {
		return cs_User_Agent;
	}

	public void setCs_User_Agent(String cs_User_Agent) {
		this.cs_User_Agent = cs_User_Agent;
	}

	public String getSc_filter_result() {
		return sc_filter_result;
	}

	public void setSc_filter_result(String sc_filter_result) {
		this.sc_filter_result = sc_filter_result;
	}

	public String getCs_categories() {
		return cs_categories;
	}

	public void setCs_categories(String cs_categories) {
		this.cs_categories = cs_categories;
	}

	public String getCs_uri_extension() {
		return cs_uri_extension;
	}

	public void setCs_uri_extension(String cs_uri_extension) {
		this.cs_uri_extension = cs_uri_extension;
	}

	public String getC_ip() {
		return c_ip;
	}

	public void setC_ip(String c_ip) {
		this.c_ip = c_ip;
	}

	public String getS_ip() {
		return s_ip;
	}

	public void setS_ip(String s_ip) {
		this.s_ip = s_ip;
	}

	public String getX_exception_id() {
		return x_exception_id;
	}

	public void setX_exception_id(String x_exception_id) {
		this.x_exception_id = x_exception_id;
	}
}
