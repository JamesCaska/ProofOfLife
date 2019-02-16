package ProofOfLifeVBB is
 
   type KeySampleData is array ( 0 .. 250 ) of Integer ;

   function CaptureKeyEvents(Samples : in KeySampleData  ) return Integer ;

end ProofOfLifeVBB;