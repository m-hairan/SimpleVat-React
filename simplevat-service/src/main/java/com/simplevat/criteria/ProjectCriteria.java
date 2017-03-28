package com.simplevat.criteria;

/**
 * Created by Utkarsh Bhavsar on 21/03/17.
 */
public class ProjectCriteria extends AbstractCriteria {

    public enum OrderBy {

        NAME("name", OrderByType.STRING),
        CANONICAL_NAME("canonicalName", OrderByType.STRING),
        ID("projectId", OrderByType.STRING),;

        private final String columnName;

        private final OrderByType columnType;

        OrderBy(String columnName, OrderByType columnType) {
            this.columnName = columnName;
            this.columnType = columnType;
        }

        public String getColumnName() {
            return columnName;
        }

        public OrderByType getColumnType() {
            return columnType;
        }
    }

    private Integer projectId;

    private Boolean active;

    private OrderBy orderBy = OrderBy.ID;

    private SortOrder sortOrder = SortOrder.ASC;

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @Override
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
