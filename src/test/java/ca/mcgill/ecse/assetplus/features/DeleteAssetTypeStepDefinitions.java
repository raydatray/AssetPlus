package ca.mcgill.ecse.assetplus.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 *  @author Team P4 
 *  Step definitions for the DeleteAssetType feature
 *  Checks that an existing asset type is no longer available in the system when deleted by a manager
 * 
 */
public class DeleteAssetTypeStepDefinitions {

	AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

	/**
	 * Loads asset types from the input dataTable
	 * 
	 * @param dataTable
	 */
	private void loadAssetTypes(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists(String.class);
		int rowIndex = 0;
		for (List<String> columns : rows) {
			if (rowIndex != 0) { // skip the header

				String name = columns.get(0);
				int lifeSpan = Integer.parseInt(columns.get(1));
				assetPlus.addAssetType(name, lifeSpan);
			}
			rowIndex++;
		}
	}

	/**
	 * Loads the input dataTable and creates the given asset types
	 * 
	 * @param dataTable
	 */
	@Given("the following asset types exist in the system \\(p4)")
	public void the_following_asset_types_exist_in_the_system_p4(io.cucumber.datatable.DataTable dataTable) {
		loadAssetTypes(dataTable);
	}

	/**
	 * Calls the deleteAssetType method to delete the asset type with the input
	 * String name
	 * 
	 * @param string
	 */
	@When("the manager attempts to delete an asset type in the system with name {string} \\(p4)")
	public void the_manager_attempts_to_delete_an_asset_type_in_the_system_with_name_p4(String string) {
		AssetPlusFeatureSet2Controller.deleteAssetType(string);
	}

	/**
	 * Checks that the number of asset types is equal to the input string number
	 * 
	 * @param string
	 */
	@Then("the number of asset types in the system shall be {string} \\(p4)")
	public void the_number_of_asset_types_in_the_system_shall_be_p4(String string) {
		int expected = Integer.parseInt(string);
		assertEquals(assetPlus.numberOfAssetTypes(), expected);
	}

	/**
	 * Checks that the input asset types in the input dataTable exist in the system
	 * 
	 * @param dataTable
	 */
	@Then("the following asset types shall exist in the system \\(p4)")
	public void the_following_asset_types_shall_exist_in_the_system_p4(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists(String.class);
		int rowIndex = 0;
		for (List<String> columns : rows) {
			if (rowIndex != 0) { // skip the header

				String name = columns.get(0);
				int lifeSpan = Integer.parseInt(columns.get(1));

				AssetType assetType = assetPlus.getAssetTypes().get(rowIndex - 1);
				boolean contained = assetType.getName().equals(name) && assetType.getExpectedLifeSpan() == lifeSpan
						&& assetType.getAssetPlus() == assetPlus;
				assertTrue(contained);
			}
			rowIndex++;
		}

	}
}
