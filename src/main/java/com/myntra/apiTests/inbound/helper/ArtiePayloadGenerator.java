package com.myntra.apiTests.inbound.helper;

import java.util.ArrayList;
import java.util.List;

public class ArtiePayloadGenerator {
	
	private String name;
	private String createdBy;
	private String createdOn;
	private String startDate;
	private String endDate;
	private String optionsRequired;
	private String categoryId;
	private String id;
	private String last_modified_by;
	private String lastModifiedOn;
	private String last_modified_on;
	private String comment;
	private String title;
	private String dropId;
	private String description;
	private String decisionTreeData;
//	private List<RecipeReferenceProducts> recipeReferenceProducts=new ArrayList<RecipeReferenceProducts>();
	private List<RecipeReferenceProducts> recipeReferenceProducts;
	private RecipeData recipeData;
	private String imageURL;
	private String optionsSubmitted;
	private String optionsApproved; 
	private String score;
	private String status;
	private String assignee;
	private String recipeId;
	private String designId;
	private String text;
	private Object[] productsToRemove;
	

	
	public Object[] getProductsToRemove() {
		return productsToRemove;
	}

	public void setProductsToRemove(Object[] productsToRemove) {
		this.productsToRemove = productsToRemove;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDesignId() {
		return designId;
	}

	public void setDesignId(String designId) {
		this.designId = designId;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOptionsSubmitted() {
		return optionsSubmitted;
	}

	public void setOptionsSubmitted(String optionsSubmitted) {
		this.optionsSubmitted = optionsSubmitted;
	}

	public String getOptionsApproved() {
		return optionsApproved;
	}

	public void setOptionsApproved(String optionsApproved) {
		this.optionsApproved = optionsApproved;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDropId() {
		return dropId;
	}

	public void setDropId(String dropId) {
		this.dropId = dropId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDecisionTreeData() {
		return decisionTreeData;
	}

	public void setDecisionTreeData(String decisionTreeData) {
		this.decisionTreeData = decisionTreeData;
	}

	public List<RecipeReferenceProducts> getRecipeReferenceProducts() {
		return recipeReferenceProducts;
	}

	public void setRecipeReferenceProducts(List<RecipeReferenceProducts> data) {
		recipeReferenceProducts=new ArrayList<RecipeReferenceProducts>();
		this.recipeReferenceProducts = data;
	}

	public RecipeData getRecipeData() {
		return recipeData;
	}

	public void setRecipeData(RecipeData recipeData) {
		this.recipeData = recipeData;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	


	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOptionsRequired() {
		return optionsRequired;
	}

	public void setOptionsRequired(String optionsRequired) {
		this.optionsRequired = optionsRequired;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	
	public String getLast_modified_by() {
		return last_modified_by;
	}

	public void setLast_modified_by(String last_modified_by) {
		this.last_modified_by = last_modified_by;
	}

	public String getLast_modified_on() {
		return last_modified_on;
	}

	public void setLast_modified_on(String last_modified_on) {
		this.last_modified_on = last_modified_on;
	}

	

}
