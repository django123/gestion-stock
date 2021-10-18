package liss.nvms.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import liss.nvms.model.AddressEntity;
import liss.nvms.model.StockEntity;




@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,String>{

	/** get All customer Address **/    
	@Query("SELECT a FROM Address a WHERE isDeleted = false "
			+ " ORDER BY code ASC")
	public Page<AddressEntity> getAllCustomerAddress(Pageable page);
	
	/** get one address **/    
	@Query("SELECT a FROM Address a  WHERE isDeleted = false AND code = :code")
	public AddressEntity getAddressByCode(@Param("code") String code);
	
	/** get one address **/    
	@Query("SELECT a FROM Address a WHERE id = :id")
	public AddressEntity getAddressById(@Param("id") String id);
	
	@Query("SELECT a FROM Address a WHERE isDeleted = false AND  UPPER(name) = UPPER(:name)")
	public AddressEntity findByName(@Param("name") String name);
	
	/** les adresses d'un customers **/
	@Query("SELECT a FROM Address a WHERE isDeleted = false AND customer.id = :customer "
			+ " ORDER BY code DESC")
	public Page<AddressEntity> getAllCustomerAddress(Pageable page, @Param("customer") String customerID );
	

}
