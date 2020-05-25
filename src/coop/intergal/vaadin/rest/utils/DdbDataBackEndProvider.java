package coop.intergal.vaadin.rest.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import coop.intergal.espresso.presutec.utils.JSonClient;

//public class DdbDataProvider  extends AbstractDataProvider<DynamicDBean, String> {
	public class DdbDataBackEndProvider  extends	AbstractBackEndDataProvider<DynamicDBean, CrudFilter> {
//	  private Department departmentFilter;
//
//	  public void setDepartmentFilter(Department department) {
//	    this.departmentFilter = department;
//	    refreshAll();
//	  }
	private ArrayList<String[]> rowsColList;// = new ArrayList<String>();
	private ArrayList<String[]> rowsFieldList;
	private ArrayList<String[]> rowsFIeldQueryList;
	private static final long serialVersionUID = 1L;
	/** Text filter that can be changed separately. */
//    private String filterText = "";
	private String resourceName;
	private String filter;
	private String preConfParam;
	private Boolean cache = true;
	private Boolean hasNewRow = false;
//	private WrappedSession keepLastWSession;
//	private String variant = "";
	private Consumer<Long> sizeChangeListener;

//	public String getVariant() {
//		return variant;
//	}
//
//	public void setVariant(String variant) {
//		this.variant = variant;
//	}

	public String getPreConfParam() {
		return preConfParam;
	}

	public void setPreConfParam(String preConfParam) {
		this.preConfParam = preConfParam;
	}

	public Boolean getCache() {
		return cache;
	}

	public void setCache(Boolean cache) {
		this.cache = cache;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

//	  @Override // query.getFilter().orElse(null)
//	  protected Stream<DynamicDBean> fetchFromBackEnd(Query<DynamicDBean, String> query) {
//		  return DataService.get().getAllDynamicDBean(query.getOffset(),query.getLimit(),cache, resourceName, preConfParam, getRowsColList(),  filter).stream();	    
//	  }

//	  @Override
//	  protected int sizeInBackEnd(Query<DynamicDBean, String> query) { //query.getFilter().orElse(null)
//		return RestData.getCountRows(getTableDbForCount(resourceName), preConfParam, filter, false);
//	  }
	
	  public ArrayList<String[]> getRowsColList() {
		 return RestData.getRowsColList(rowsColList, resourceName, preConfParam, null);
	  }
	  public ArrayList<String[]> getRowsColList(boolean cache) {
		 return RestData.getRowsColList(rowsColList, resourceName, preConfParam, cache);
	  }
	  public ArrayList<String[]> getRowsFieldList() {
		 return RestData.getRowsFieldList(rowsFieldList, resourceName, preConfParam, null);	
	  }	
	  
	  public ArrayList<String[]> getRowsFieldList(boolean cache) {
		  return RestData.getRowsFieldList(rowsFieldList, resourceName, preConfParam, cache);	
		}
		 
	  public ArrayList<String[]> getRowsQueryFieldList(boolean b) {
		  return RestData.getRowsQueryFieldList(rowsFIeldQueryList, resourceName, preConfParam, cache);	
	  	} 


		public void setRowsColList(ArrayList<String[]> rowsColList) {
			this.rowsColList = rowsColList;
		}


		public void setRowsFieldList(ArrayList<String[]> rowsFieldList) {
			this.rowsFieldList = rowsFieldList;
		}

		public void setRowsFIeldQueryList(ArrayList<String[]> rowsFIeldQueryList) {
			this.rowsFIeldQueryList = rowsFIeldQueryList;
		}

		public Boolean getHasNewRow() {
			return hasNewRow;
		}

		public void setHasNewRow(Boolean hasNewRow) {
			this.hasNewRow = hasNewRow;
		}

		private String getTableDbForCount(String resourceName2) {    // TODO check if is re-use in other classes and then add to a generic class
			if (resourceName2.startsWith("@")) // is use for system tables, as it is 
				return resourceName2;
			int startIdx = 3; // mormal format is CR_tableName__Variation
			if (resourceName2.startsWith("CR-") == false) // for using of tables without resources, when is a resource it starts with CR_
				startIdx = 0;
			int endIdx = resourceName2.indexOf("__");
			if (endIdx < 0)
				endIdx=resourceName2.length();
			int postPoint = resourceName2.indexOf(".");
			if (postPoint > 0)
			{
				startIdx = postPoint +1;
				endIdx=resourceName2.length();
			}	
			return resourceName2.substring(startIdx, endIdx);
		}

//		@Override
//		public Stream<DynamicDBean> fetch(com.vaadin.flow.data.provider.Query<DynamicDBean, String> query ) {
//			return DataService.get().getAllDynamicDBean(query.getOffset(),query.getLimit(),cache, resourceName, preConfParam, getRowsColList(),  filter).stream();
//	//		return null;
//		}

		@Override
		public boolean isInMemory() {
			// TODO Auto-generated method stub
			return false;
		}

		  @Override
		    protected Stream<DynamicDBean> fetchFromBackEnd(Query<DynamicDBean, CrudFilter> query) {
		        int offset = query.getOffset();
		        int limit = query.getLimit();
		        List<QuerySortOrder> sortOrdersFields = query.getSortOrders();
		        Stream<DynamicDBean> stream = DataService.get().getAllDynamicDBean(query.getOffset(),query.getLimit(),cache, resourceName, preConfParam, getRowsColList(),  filter,sortOrdersFields, hasNewRow).stream();

		        if (query.getFilter().isPresent()) {
		            stream = stream
		                    .filter(predicate(query.getFilter().get()))
		                    .sorted(comparator(query.getFilter().get()));
		        }
	//	        System.out.println("DdbDataBackEndProvider.fetchFromBackEnd()......."+ stream.toArray().length);
		        return stream;//.skip(offset).limit(limit);
		    }

		    @Override
		    protected int sizeInBackEnd(Query<DynamicDBean, CrudFilter> query) {
		        // For RDBMS just execute a SELECT COUNT(*) ... WHERE query
		        long count = RestData.getCountRows(getTableDbForCount(resourceName), preConfParam, filter, false, hasNewRow);//fetchFromBackEnd(query).count();

		        if (sizeChangeListener != null) {
		            sizeChangeListener.accept(count);
		        }

		        return (int) count;
		    }

		    void setSizeChangeListener(Consumer<Long> listener) {
		        sizeChangeListener = listener;
		    }

//			@Override
//			protected int sizeInBackEnd(Query<DynamicDBean, CrudFilter> query) {
//				return RestData.getCountRows(getTableDbForCount(resourceName), preConfParam, filter, false);
//			}
		    private static Predicate<DynamicDBean> predicate(CrudFilter filter) {
		        // For RDBMS just generate a WHERE clause
		        return filter.getConstraints().entrySet().stream()
		                .map(constraint -> (Predicate<DynamicDBean>) person -> {
		                    try {
		                        Object value = valueOf(constraint.getKey(), person);
		                        return value != null && value.toString().toLowerCase()
		                                .contains(constraint.getValue().toLowerCase());
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                        return false;
		                    }
		                })
		                .reduce(Predicate::and)
		                .orElse(e -> true);
		    }
		    private static Object valueOf(String fieldName, DynamicDBean dynamicDBean) {
		        try {
		            Field field = DynamicDBean.class.getDeclaredField(fieldName);
		            field.setAccessible(true);
		            return field.get(dynamicDBean);
		        } catch (Exception ex) {
		            throw new RuntimeException(ex);
		        }
		    }
		    private static Comparator<DynamicDBean> comparator(CrudFilter filter) {
		        // For RDBMS just generate an ORDER BY clause
		        return filter.getSortOrders().entrySet().stream()
		                .map(sortClause -> {
		                    try {
		                        Comparator<DynamicDBean> comparator
		                                = Comparator.comparing(dynamicDBean ->
		                                (Comparable) valueOf(sortClause.getKey(), dynamicDBean));

		                        if (sortClause.getValue() == SortDirection.DESCENDING) {
		                            comparator = comparator.reversed();
		                        }

		                        return comparator;
		                    } catch (Exception ex) {
		                        return (Comparator<DynamicDBean>) (o1, o2) -> 0;
		                    }
		                })
		                .reduce(Comparator::thenComparing)
		                .orElse((o1, o2) -> 0);
		    }

			public void save(String ResourceTobeSave, Hashtable<String, DynamicDBean> beansToSaveAndRefresh) {
				DynamicDBean firstBean = beansToSaveAndRefresh.get(ResourceTobeSave);
				boolean newProduct = firstBean.getCol0()== null ;
		        
		        DataService.get().updateDynamicDBean(ResourceTobeSave, beansToSaveAndRefresh);
		        if (newProduct) {
		            refreshAll();
		        } else {
		            refreshItem(firstBean);
		        }
//				return selectedRow;
		    }
			public void delete(String ResourceTobeSave, Hashtable<String, DynamicDBean> beansToSaveAndRefresh) {
				DynamicDBean firstBean = beansToSaveAndRefresh.get(ResourceTobeSave);
				boolean newProduct = firstBean.getCol0()== null ;
		        
		        DataService.get().deleteDynamicDBean(ResourceTobeSave, beansToSaveAndRefresh);
		        refreshAll();
//				return selectedRow;
		    }

			public void refresh(DynamicDBean selectedRow) {
				 refreshItem(selectedRow);
				
			}

			public void insertANewRow() {
				// TODO Auto-generated method stub
				
			}




				
			


	}
