package com.cbsinc.cms;

import java.io.Serializable;
import java.util.List;

public class GetValueTool implements Serializable {

	/**
	 *
	 */
	transient private static final long serialVersionUID = -7024178057683148016L;

	/**
	 * <p>
	 * Title: Content Manager System
	 * </p>
	 * <p>
	 * Description: System building web application develop by Konstantin Grabko.
	 * Konstantin Grabko is Owner and author this code. You can not use it and you
	 * cannot change it without written permission from Konstantin Grabko Email:
	 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2002-2025
	 * </p>
	 * <p>
	 * Company: CENTER BUSINESS SOLUTIONS INC
	 * </p>
	 *
	 * @author Konstantin Grabko
	 * @version 1.0
	 */

	// private static final long serialVersionUID = 1L;

	public String getValueAt(List list, int aRow, int aColumn) {
		String[] row = (String[]) list.get(aRow);
		return row[aColumn];
	}

	public Object getValueAtObject(List list, int aRow, int aColumn) {
		Object[] row = (Object[]) list.get(aRow);
		return row[aColumn];
	}

	public int getRowCount(List list) {
		return list.size();
	}

}
