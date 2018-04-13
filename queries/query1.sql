@query1
SELECT sum(price) AS Total_Cost FROM hong1.model_part, hong1.part WHERE hong1.model_part.pid=hong1.part.pid AND hong1.model_part.mid=10002;