/*
 * Copyright 2016 (C) Tom Parker <thpr@users.sourceforge.net>
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
package pcgen.base.formula.base;

/**
 * A ManagerFactory is an object designed to produce the various manager objects used by
 * the visitors to a Formula. This is an interface to allow extension of these behaviors
 * by more advanced formula processing systems.
 */
public interface ManagerFactory
{
	/**
	 * Generates an initialized DependencyManager with the given arguments.
	 * 
	 * @param formulaManager
	 *            The FormulaManager to be contained in the DependencyManager
	 * @param scopeInst
	 *            The ScopeInstance to be contained in the DependencyManager
	 * @param assertedFormat
	 *            The format currently asserted for the formula served by the
	 *            DependencyManager
	 * @return An initialized DependencyManager with the given arguments
	 */
	public default DependencyManager generateDependencyManager(
		FormulaManager formulaManager, ScopeInstance scopeInst, Class<?> assertedFormat)
	{
		DependencyManager fdm = new DependencyManager();
		fdm.set(DependencyManager.FMANAGER, formulaManager);
		fdm.set(DependencyManager.INSTANCE, scopeInst);
		fdm.set(DependencyManager.ASSERTED, assertedFormat);
		return fdm;
	}

	/**
	 * Constructs and initializes a new FormulaSemantics object with the appropriate keys
	 * set to the given parameters.
	 * 
	 * @param manager
	 *            The FormulaManager referenced when a Formula is processed with this
	 *            FormulaSemantics
	 * @param legalScope
	 *            The LegalScope when a Formula is processed with this FormulaSemantics
	 * @param assertedFormat
	 *            The asserted Format when a Formula is processed with this
	 *            FormulaSemantics (may be null)
	 * @return An initialized FormulaSemantics object with the appropriate keys set to the
	 *         given parameters
	 */
	public default FormulaSemantics generateFormulaSemantics(FormulaManager manager,
		LegalScope legalScope, Class<?> assertedFormat)
	{
		FormulaSemantics semantics = new FormulaSemantics();
		semantics.set(FormulaSemantics.FMANAGER, manager);
		semantics.set(FormulaSemantics.SCOPE, legalScope);
		semantics.set(FormulaSemantics.ASSERTED, assertedFormat);
		return semantics;
	}

	/**
	 * Generates a new EvaluationManager initialized with the given parameters.
	 * 
	 * @param formulaManager
	 *            The FormulaManager used to evaluate formulas processed by this
	 *            EvaluationManager
	 * @param scopeInst
	 *            The ScopeInstance of formulas processed by this EvaluationManager
	 * @param assertedFormat
	 *            the format (class) asserted by the current context of a formula
	 * @return A new EvaluationManager initialized with the given parameters
	 */
	public default EvaluationManager generateEvaluationManager(
		FormulaManager formulaManager, ScopeInstance scopeInst, Class<?> assertedFormat)
	{
		EvaluationManager manager = new EvaluationManager();
		manager.set(EvaluationManager.FMANAGER, formulaManager);
		manager.set(EvaluationManager.INSTANCE, scopeInst);
		manager.set(EvaluationManager.ASSERTED, assertedFormat);
		return manager;
	}

	/**
	 * Generates a new EvaluationManager initialized with the given FormulaManager and the
	 * ScopeInstance and asserted format as implied by the given VariableID.
	 * 
	 * @param formulaManager
	 *            The FormulaManager used to evaluate formulas processed by this
	 *            EvaluationManager
	 * @param varID
	 *            The VariableID to be used to derive the the ScopeInstance and asserted
	 *            format of the EvaluationManager
	 * @return A new EvaluationManager initialized with the given FormulaManager and the
	 *         ScopeInstance Asserted Format of the given VariableID
	 */
	public default EvaluationManager generateEvaluationManager(
		FormulaManager formulaManager, VariableID<?> varID)
	{
		EvaluationManager manager = new EvaluationManager();
		manager.set(EvaluationManager.FMANAGER, formulaManager);
		manager.set(EvaluationManager.INSTANCE, varID.getScope());
		manager.set(EvaluationManager.ASSERTED, varID.getVariableFormat());
		return manager;
	}

}