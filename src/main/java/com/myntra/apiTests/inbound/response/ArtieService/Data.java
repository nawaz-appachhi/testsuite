package com.myntra.apiTests.inbound.response.ArtieService;

import com.myntra.apiTests.inbound.helper.RecipeReferenceProducts;
import com.myntra.apiTests.inbound.helper.RecipeData;

public class Data {

	private String createdOn;

	private String id;

	private String startDate;

	private String createdBy;

	private String optionsRequired;

	private String categoryId;

	private String name;

	private String optionsApproved;

	private String endDate;

	private String lastModifiedOn;
	
	private String optionsSubmitted;

	private String status;

	private String dropId;

	private String score;

	private RecipeReferenceProducts[] recipeReferenceProducts;

	private RecipeData recipeData;

	private String title;

	private String description;

	private String decisionTreeData;

	private String imageURL;
	
	private String recipeId;
	
	private String designId;

	public String getRecipeId() {
		return recipeId;
	}

	public String getDesignId() {
		return designId;
	}

	public void setDesignId(String designId) {
		this.designId = designId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public RecipeReferenceProducts[] getRecipeReferenceProducts() {
		return recipeReferenceProducts;
	}

	public void setRecipeReferenceProducts(RecipeReferenceProducts[] recipeReferenceProducts) {
		this.recipeReferenceProducts = recipeReferenceProducts;
	}

	public RecipeData getRecipeData() {
		return recipeData;
	}

	public void setRecipeData(RecipeData recipeData) {
		this.recipeData = recipeData;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	

	public String getDropId() {
		return dropId;
	}

	public void setDropId(String dropId) {
		this.dropId = dropId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getOptionsApproved() {
		return optionsApproved;
	}

	public void setOptionsApproved(String optionsApproved) {
		this.optionsApproved = optionsApproved;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public String getOptionsSubmitted() {
		return optionsSubmitted;
	}

	public void setOptionsSubmitted(String optionsSubmitted) {
		this.optionsSubmitted = optionsSubmitted;
	}

	@Override
	public String toString() {
		return "ClassPojo [createdOn = " + createdOn + ", id = " + id + ", startDate = " + startDate + ", createdBy = "
				+ createdBy + ", optionsRequired = " + optionsRequired + ", categoryId = " + categoryId + ", name = "
				+ name + ", optionsApproved = " + optionsApproved + ", endDate = " + endDate + ", lastModifiedOn = "
				+ lastModifiedOn + ", optionsSubmitted = " + optionsSubmitted + "]";
	}
}
