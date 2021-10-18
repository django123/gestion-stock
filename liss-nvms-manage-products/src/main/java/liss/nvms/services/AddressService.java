package liss.nvms.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liss.nvms.date.GenerateReference;
import liss.nvms.httpException.HttpServiceExceptionHandle;
import liss.nvms.model.AddressEntity;
import liss.nvms.model.CommandLineCustomerEntity;
import liss.nvms.model.CustomerEntity;
import liss.nvms.model.StockEntity;
import liss.nvms.repository.AddressRepository;
import liss.nvms.repository.CustomerRepository;
import liss.nvms.utils.HttpErrorCodes;

@Service
@Transactional(rollbackFor = {HttpServiceExceptionHandle.class, SQLException.class }, noRollbackFor = EntityNotFoundException.class)
public class AddressService {
	
	@Autowired
	private AddressRepository addressRep;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	/*** ajouter une adresse customer ***/
	
	 public AddressEntity addAddress(AddressEntity addressEntity) {
		 try {
			  String code = "#NVA"+System.currentTimeMillis();
			  if(addressEntity.getCustomer() == null || addressEntity.getCustomer().getId() == null ) throw new HttpServiceExceptionHandle("Client introuvable !!", HttpErrorCodes.INTERNAL_SERVER_ERROR);
			  AddressEntity entity = addressRep.getAddressById(addressEntity.getId());
			  if(entity !=  null) throw new HttpServiceExceptionHandle("adresse déja existant",HttpErrorCodes.INTERNAL_SERVER_ERROR);
			  addressEntity.setCode(code);
			 return addressRep.save(addressEntity);
			
		} catch (HttpServiceExceptionHandle e) {
			HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
			throw new HttpServiceExceptionHandle(e.getMessage(),code);
		}
	 }
	 
		/** aficher la liste des address  customers **/
		public  Map<String, Object> getAllAddress(String customer, int page, int limit) {
			
			try {
				Map<String, Object> results = new HashMap<>();
				Pageable paging = PageRequest.of( page, limit);
				Page<AddressEntity> stockPage = null;
				if(customer == null) stockPage = addressRep.getAllCustomerAddress(paging);
				else  stockPage = addressRep.getAllCustomerAddress(paging, customer);
				if(stockPage == null) new HashMap<>();
				results.put("data", stockPage.getContent());
				results.put("currentPage", stockPage.getNumber());
				results.put("totalItems", stockPage.getTotalElements());
				results.put("totalPages", stockPage.getTotalPages());
				 return results;

			}catch (HttpServiceExceptionHandle e) {
				HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
				throw new HttpServiceExceptionHandle(e.getMessage(),code);
			}
			
		}
		
		
		/** modifier une adresses **/
		public AddressEntity updateAddress(AddressEntity address) {
				
		 try {
			  AddressEntity add = addressRep.getAddressById(address.getId());
			  if(add  == null) new HttpServiceExceptionHandle("Adresse introuvable",HttpErrorCodes.INTERNAL_SERVER_ERROR);
			  add.setName(address.getName());
			  add.setStreet(address.getStreet());
			  add.setPostalCode(address.getPostalCode());
			  return addressRep.save(add);
			}catch (HttpServiceExceptionHandle e) {
					HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
			 throw new HttpServiceExceptionHandle(e.getMessage(),code);
			}
				
	}
			
		  /**  suppression d'une adresse**/

		  public boolean deleteAddress(String reference){
		  	try {
		  		AddressEntity address = addressRep.getAddressById(reference);
				if(address == null) throw new HttpServiceExceptionHandle("address introuvable",HttpErrorCodes.INTERNAL_SERVER_ERROR);
				if(address.getIsDeleted() == true) address.setIsDeleted(false);
				else address.setIsDeleted(true);
				addressRep.save(address);
				return true;
			}catch (HttpServiceExceptionHandle e) {
				HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
				throw new HttpServiceExceptionHandle(e.getMessage(),code);
			}
		  }

}
