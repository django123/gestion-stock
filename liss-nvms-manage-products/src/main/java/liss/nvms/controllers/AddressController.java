package liss.nvms.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import liss.nvms.httpException.HttpServiceExceptionHandle;
import liss.nvms.model.AddressEntity;
import liss.nvms.services.AddressService;
import liss.nvms.utils.HttpErrorCodes;
import liss.nvms.utils.HttpErrorMessage;

@RestController
@RequestMapping("address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	/** afficher tous les adresses **/
	@GetMapping
	public ResponseEntity<?> getAddress(HttpServletRequest request, @RequestParam(value = "customer", required = false) String query,
									@RequestParam(value = "page", required = false, defaultValue = "0") int page,
									@RequestParam(value = "limit", required = false, defaultValue = "5000") int limit){
		try{
			 return new ResponseEntity<>(addressService.getAllAddress(query, page, limit), HttpStatus.OK);
		}catch (HttpServiceExceptionHandle e) {
			return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
		}

	}
	
	
	/** ajouter une adresse **/
	@PostMapping
	public ResponseEntity<?> addAddress(@RequestBody AddressEntity customer){
		try{
			
			if(customer == null) throw new HttpServiceExceptionHandle("Contenue vide !!", HttpErrorCodes.BAD_FORMAT_DATA);
			
			 return new ResponseEntity<>(addressService.addAddress(customer), HttpStatus.OK);
		}catch (HttpServiceExceptionHandle e) {
			return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
		}
	}
	
	/** modifier  une adresse **/
	@PutMapping
	public ResponseEntity<?> updateAddress(@RequestBody AddressEntity address){
		try{
			if(address == null) throw new HttpServiceExceptionHandle("Contenue vide !!", HttpErrorCodes.BAD_FORMAT_DATA);
			 return new ResponseEntity<>(addressService.updateAddress(address), HttpStatus.OK);
		}catch (HttpServiceExceptionHandle e) {
			return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
		}

	}
	
	/** delete address**/
	@GetMapping("delete")
	public ResponseEntity<?> deleteCustomer(HttpServletRequest request, @RequestParam(value = "reference", required = true) String param){
		try{
			return new ResponseEntity<>(addressService.deleteAddress(param), HttpStatus.OK);
		}catch (HttpServiceExceptionHandle e) {
			return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
		}
	}
}
