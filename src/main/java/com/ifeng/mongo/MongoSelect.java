package com.ifeng.mongo;

import com.ifeng.core.query.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoSelect extends Select{
	private List<String> groupBy;
	private List<OrderBy> orderBy;
	protected Where condition = new Where();

	public MongoSelect(){
		super(new ArrayList<SelectField>(),0,Integer.MAX_VALUE);
		this.groupBy  = new ArrayList<String>();
		this.orderBy = new ArrayList<OrderBy>();
		condition = new Where();
	}

	public MongoSelect(Where where){
		super(new ArrayList<SelectField>(),0,Integer.MAX_VALUE);
		this.groupBy  = new ArrayList<String>();
		this.orderBy = new ArrayList<OrderBy>();
		condition = where;
	}

	public MongoSelect(List<SelectField> fields,Where where ,int pageIndex,int pageSize){
		super(fields,pageIndex,pageSize);
		this.groupBy  = new ArrayList<String>();
		this.orderBy = new ArrayList<OrderBy>();
		condition = where;
	}
	public MongoSelect(List<SelectField> fields){
		super(fields,0,Integer.MAX_VALUE);
		this.groupBy  = new ArrayList<String>();
		this.orderBy = new ArrayList<OrderBy>();
		condition = new Where();
	}
	
	public MongoSelect(List<SelectField> fields,int pageIndex,int pageSize){
		super(fields,pageIndex,pageSize);
		this.groupBy  = new ArrayList<String>();
		this.orderBy = new ArrayList<OrderBy>();
		condition = new Where();
	}
	

	/**
	 * 增加分组字段
	 * @param field
	 * @return
	 */
	public MongoSelect groupBy(String field)
    {
		groupBy.add(field);
        return this;
    }

	/**
	 * 添加排序字段
	 * @param orderBy 排序实例
	 * @return
	 */
	public MongoSelect orderBy(OrderBy orderBy)
    {
		this.orderBy.add(orderBy);
        return this;
    }

	/**
	 * 增加筛选条件 按照等于处理
	 * @param name
	 * @param value
	 * @return
	 */
	public Select where(String name, Object value)
	{
		where(name, WhereType.Equal, value);
		return this;
	}

	/**
	 * 增加筛选条件
	 * @param name 字段名称
	 * @param whereType 条件
	 * @param value 值
	 * @return
	 */
	public Select where(String name, WhereType whereType, Object value)
	{
		this.condition.getAndList().add(new WhereItem(name, whereType, value));
		return this;
	}
	/**
	 * 添加排序字段
	 * @param name
	 * @param direction
	 * @return
	 */
	public MongoSelect orderBy(String name,OrderByDirection direction)
	{
		this.orderBy.add(new OrderBy(name,direction));
		return this;
	}

	List<String> getGroupBy() {
		return groupBy;
	}

	List<OrderBy> getOrderBy() {
		return orderBy;
	}

	Document createFieldsDocument(){
		Document fields = new Document();
		if (super.getFields() != null) {
			for (SelectField item : super.getFields()) {
				fields.put(item.getAlias(), true);
			}
		}

		return fields;
	}

	Document createSortDocument() {
		Document sort = new Document();
		if (this.getOrderBy() != null && this.getOrderBy().size() > 0) {

			for (OrderBy item : this.getOrderBy()) {
				sort.put(item.name, item.direction.value());
			}
		}
		return sort;
	}

	public Where getCondition() {
		return condition;
	}
}