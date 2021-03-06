/*
 * Copyright 2014 (C) Tom Parker <thpr@users.sourceforge.net>
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pcgen.base.testsupport;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;
import pcgen.base.formula.parse.FormulaParser;
import pcgen.base.formula.parse.ParseException;
import pcgen.base.formula.parse.SimpleNode;

public final class TestUtilities
{
	public static final double SMALL_ERROR = Math.pow(10, -10);


	private TestUtilities()
	{
		//Do not instantiate utility class
	}

	public static SimpleNode doParse(String formula)
	{
		try
		{
			return new FormulaParser(new StringReader(formula)).query();
		}
		catch (ParseException e)
		{
			TestCase
				.fail("Encountered Unexpected Exception: " + e.getMessage());
			return null;
		}
	}

	public static boolean doubleEqual(double d1, double d2, double delta)
	{
		if (delta < 0)
		{
			throw new IllegalArgumentException(
				"Delta for doubleEqual cannot be < 0: " + delta);
		}
		double diff = d1 - d2;
		return ((diff >= 0) && (diff < delta))
			|| ((diff < 0) && (diff > -delta));
	}


	/**
	 * Utility method for Unit tests to invoke private constructors
	 * 
	 * @param clazz The class we're gonig to invoke the constructor on
	 * @return An instance of the class
	 */
	public static Object invokePrivateConstructor(Class<?> clazz)
	{
		Constructor<?> constructor = null; 
		try
		{
			constructor = clazz.getDeclaredConstructor();
		}
		catch (NoSuchMethodException e)
		{
			System.err.println("Constructor for [" + clazz.getName() + "] does not exist");
		}
		
		constructor.setAccessible(true);
		Object instance = null;
		
		try
		{
			instance = constructor.newInstance();
		}
		catch (InvocationTargetException | InstantiationException ite)
		{
			System.err.println("Instance creation failed with [" + ite.getCause() + "]");
		}
		catch (IllegalAccessException iae)
		{
			System.err.println("Instance creation failed due to access violation.");
		}

		return instance;
	}
}
