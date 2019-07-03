package coop.intergal.vaadin.rest.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import com.vaadin.flow.data.provider.QuerySortOrder;


/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    public static DataService get() {
        return MockDataService.getInstance();
    }

	public abstract Collection<DynamicDBean> getAllDynamicDBean(int offset, int limit, boolean b, String s, String s0, ArrayList<String[]> arrayList, String filtro, List<QuerySortOrder> sortOrdersFields, Boolean hasNewRow) ;

	public abstract DynamicDBean getDynamicDBeanById(int productId) ;
	public abstract void updateDynamicDBean(DynamicDBean customer) ;

	public abstract void updateDynamicDBean(String resourceTobeSave, Hashtable<String, DynamicDBean> beansToSaveAndRefresh) ;
	public abstract void deleteDynamicDBean(String resourceTobeSave, Hashtable<String, DynamicDBean> beansToSaveAndRefresh) ;

	public abstract void showError(String string);

}
	


