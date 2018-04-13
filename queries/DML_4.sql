-- Scrap a ship under construction

-- delete contract
delete from hong1.contract
	where contid = integer(associted with the contract id to be deleted);

-- delete cont_part relations
delete from hong1.cont_part
	where contid = integer(this should delete all the tuples with same contid);