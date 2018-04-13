-- Update the cost of an item (only affects ships that buy that part in the future)

-- Update part price
update hong1.part
	set price = integer
	where pid = integer;

-- Update ship labor
update hong1.model
	set labor = integer
	where mid = integer;

-- Update ship markup
update hong1.model
	set markup = integer
	where mid = integer;