package com.myntra.apiTests.inbound.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private String article_type;
	private Attrs attrs;
	private String styleId;
	private String revLast15;
	private String sum_of_cd;
	private String activated_at;

	private String quantity_last_6_months;

	private String asp_with_discount;

	private String first_purchase_date;

	private String p_td_with_discount;

	private String sum_of_td;
	private String style_break_date;
	private String style_catalogued_date;

	private String ros_with_discount;

	private String p_cd;

	private String Fit;

	private String brand;

	private String style_date;

	private String days_with_discount;

	private String Care;

	//
	// private String Neck or Collar;
	//
	// private String Sleeve Type;

	private String gender;

	private String quantity;

	private String Occasion;

	private String asp_without_discount;

	// private String Brand Fit Name;

	private String list_view_share;

	private String ror;

	private String days_without_discount;

	private String p_td_without_discount;

	private String ld;

	private String pdp_count;

	private String ros_without_discount;

	private double asp;

	private String style_id;

	private String mrp;

	private String list_to_pdp;

	private String list_count;

	private String articleAttributes;

	private String revenue;

	private String quantity_1k_per_list_view;

	private String add_to_cart_count;

	public double getAsp() {
		return asp;
	}

	public String getStyle_break_date() {
		return style_break_date;
	}

	public String getSum_of_cd() {
		return sum_of_cd;
	}

	public void setSum_of_cd(String sum_of_cd) {
		this.sum_of_cd = sum_of_cd;
	}

	public String getQuantity_last_6_months() {
		return quantity_last_6_months;
	}

	public void setQuantity_last_6_months(String quantity_last_6_months) {
		this.quantity_last_6_months = quantity_last_6_months;
	}

	public String getAsp_with_discount() {
		return asp_with_discount;
	}

	public void setAsp_with_discount(String asp_with_discount) {
		this.asp_with_discount = asp_with_discount;
	}

	public String getFirst_purchase_date() {
		return first_purchase_date;
	}

	public void setFirst_purchase_date(String first_purchase_date) {
		this.first_purchase_date = first_purchase_date;
	}

	public String getP_td_with_discount() {
		return p_td_with_discount;
	}

	public void setP_td_with_discount(String p_td_with_discount) {
		this.p_td_with_discount = p_td_with_discount;
	}

	public String getSum_of_td() {
		return sum_of_td;
	}

	public void setSum_of_td(String sum_of_td) {
		this.sum_of_td = sum_of_td;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getOccasion() {
		return Occasion;
	}

	public void setOccasion(String Occasion) {
		this.Occasion = Occasion;
	}

	public String getAsp_without_discount() {
		return asp_without_discount;
	}

	public void setAsp_without_discount(String asp_without_discount) {
		this.asp_without_discount = asp_without_discount;
	}

	public String getList_view_share() {
		return list_view_share;
	}

	public void setList_view_share(String list_view_share) {
		this.list_view_share = list_view_share;
	}

	public String getRor() {
		return ror;
	}

	public void setRor(String ror) {
		this.ror = ror;
	}

	public String getDays_without_discount() {
		return days_without_discount;
	}

	public void setDays_without_discount(String days_without_discount) {
		this.days_without_discount = days_without_discount;
	}

	public String getP_td_without_discount() {
		return p_td_without_discount;
	}

	public void setP_td_without_discount(String p_td_without_discount) {
		this.p_td_without_discount = p_td_without_discount;
	}

	public String getLd() {
		return ld;
	}

	public void setLd(String ld) {
		this.ld = ld;
	}

	public String getPdp_count() {
		return pdp_count;
	}

	public void setPdp_count(String pdp_count) {
		this.pdp_count = pdp_count;
	}

	public String getRos_without_discount() {
		return ros_without_discount;
	}

	public void setRos_without_discount(String ros_without_discount) {
		this.ros_without_discount = ros_without_discount;
	}

	public void setAsp(double asp) {
		this.asp = asp;
	}

	public String getStyle_id() {
		return style_id;
	}

	public void setStyle_id(String style_id) {
		this.style_id = style_id;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getList_to_pdp() {
		return list_to_pdp;
	}

	public void setList_to_pdp(String list_to_pdp) {
		this.list_to_pdp = list_to_pdp;
	}

	public String getList_count() {
		return list_count;
	}

	public void setList_count(String list_count) {
		this.list_count = list_count;
	}

	public String getArticleAttributes() {
		return articleAttributes;
	}

	public void setArticleAttributes(String articleAttributes) {
		this.articleAttributes = articleAttributes;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getQuantity_1k_per_list_view() {
		return quantity_1k_per_list_view;
	}

	public void setQuantity_1k_per_list_view(String quantity_1k_per_list_view) {
		this.quantity_1k_per_list_view = quantity_1k_per_list_view;
	}

	public String getAdd_to_cart_count() {
		return add_to_cart_count;
	}

	public void setAdd_to_cart_count(String add_to_cart_count) {
		this.add_to_cart_count = add_to_cart_count;
	}

	public void setStyle_break_date(String style_break_date) {
		this.style_break_date = style_break_date;
	}

	public void setStyle_catalogued_date(String style_catalogued_date) {
		this.style_catalogued_date = style_catalogued_date;
	}

	public String getRos_with_discount() {
		return ros_with_discount;
	}

	public void setRos_with_discount(String ros_with_discount) {
		this.ros_with_discount = ros_with_discount;
	}

	public String getP_cd() {
		return p_cd;
	}

	public void setP_cd(String p_cd) {
		this.p_cd = p_cd;
	}

	public String getFit() {
		return Fit;
	}

	public void setFit(String Fit) {
		this.Fit = Fit;
	}

	public String getStyle_date() {
		return style_date;
	}

	public void setStyle_date(String style_date) {
		this.style_date = style_date;
	}

	public String getDays_with_discount() {
		return days_with_discount;
	}

	public void setDays_with_discount(String days_with_discount) {
		this.days_with_discount = days_with_discount;
	}

	public String getCare() {
		return Care;
	}

	public void setCare(String Care) {
		this.Care = Care;
	}

	public String getStyle_catalogued_date() {
		return style_catalogued_date;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getP_td() {
		return p_td;
	}

	public void setP_td(double p_td) {
		this.p_td = p_td;
	}

	public double getRos() {
		return ros;
	}

	public void setRos(Integer ros) {
		this.ros = ros;
	}

	public Integer getDays_live() {
		return days_live;
	}

	public void setDays_live(Integer days_live) {
		this.days_live = days_live;
	}

	public String getSeason_code() {
		return season_code;
	}

	public void setSeason_code(String season_code) {
		this.season_code = season_code;
	}

	private double p_td;
	private double ros;
	private Integer days_live;
	private String season_code;

	public Attrs getAttrs() {
		return attrs;
	}

	public void setAttrs(Attrs attrs) {
		this.attrs = attrs;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getArticle_type() {
		return article_type;
	}

	public void setArticle_type(String article_type) {
		this.article_type = article_type;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRevLast15() {
		return revLast15;
	}

	public void setRevLast15(String revLast15) {
		this.revLast15 = revLast15;
	}

	@Override
	public String toString() {
		return "ClassPojo [sum_of_cd = " + sum_of_cd + ", quantity_last_6_months = " + quantity_last_6_months
				+ ", asp_with_discount = " + asp_with_discount + ", first_purchase_date = " + first_purchase_date
				+ ", p_td_with_discount = " + p_td_with_discount + ", sum_of_td = " + sum_of_td + ", season_code = "
				+ season_code + ", days_live = " + days_live + ",  gender = " + gender + ", quantity = " + quantity
				+ ", Occasion = " + Occasion + ", p_td = " + p_td + ", asp_without_discount = " + asp_without_discount
				+ ",  list_view_share = " + list_view_share + ", ror = " + ror + ", ros = " + ros
				+ ", days_without_discount = " + days_without_discount + ",  p_td_without_discount = "
				+ p_td_without_discount + ", ld = " + ld + ", pdp_count = " + pdp_count + ",  ros_without_discount = "
				+ ros_without_discount + ", asp = " + asp + ", style_id = " + style_id + ",  mrp = " + mrp
				+ ", list_to_pdp = " + list_to_pdp + ", list_count = " + list_count + ",  articleAttributes = "
				+ articleAttributes + ",  revenue = " + revenue + ",  quantity_1k_per_list_view = "
				+ quantity_1k_per_list_view + ", add_to_cart_count = " + add_to_cart_count + ",  style_break_date = "
				+ style_break_date + ",  style_catalogued_date = " + style_catalogued_date + ", ros_with_discount = "
				+ ros_with_discount + ", p_cd = " + p_cd + ", Fit = " + Fit + ", brand = " + brand + ", style_date = "
				+ style_date + ", days_with_discount = " + days_with_discount + ", Care = " + Care + "activated_at = "+activated_at+"]";
	}

	public String getActivated_at() {
		return activated_at;
	}

	public void setActivated_at(String activated_at) {
		this.activated_at = activated_at;
	}

	

}
