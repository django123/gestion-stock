package liss.nvms.invoice.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressClass {
	

	private String name = "";
	
    private String postalCode = "";
	

	private String street = "";

}
