SELECT hong1.contract.contid, hong1.customer.FIRSTNAME, hong1.customer.LASTNAME, hong1.model.name, hong1.department.name, hong1.contract.invoice FROM
	hong1.contract, hong1.model, hong1.department, hong1.customer
	WHERE hong1.contract.cusid=hong1.customer.cusid AND hong1.contract.mid=hong1.model.mid AND hong1.contract.did=hong1.department.did;