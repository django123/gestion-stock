package liss.nvms.additional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import liss.nvms.Flivraison.DeliveryLineSupplierEntity;

@Repository
public interface DeliveryLineSupplierRepository extends JpaRepository<DeliveryLineSupplierEntity, String> {

}
