SELECT hong1.part.PID, NAME, STATUS FROM hong1.contract, hong1.cont_part, hong1.part
	WHERE hong1.contract.contid=12345671 AND hong1.contract.contid=hong1.cont_part.contid
		AND hong1.cont_part.pid=hong1.part.pid;