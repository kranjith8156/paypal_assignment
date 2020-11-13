package com.paypal.bfs.test.employeeserv.serivce;

import com.paypal.bfs.test.employeeserv.api.model.Address;

public interface AddressService {
	void updateAddress(Address address);
	Address findAddressById(Long id);
}
