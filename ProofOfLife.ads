with ProofOfLifeVBB;


package ProofOfLife is
  
   procedure ProofOfLife(Samples : in ProofOfLifeVBB.KeySampleData  ; LastSampleIndex : in Integer )  ;

   function IsNoKeyAttack(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer ) return Boolean;

   function IsKeyScanningAttack(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer ) return Boolean;

   function UniqueKeyPresses(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer ) return Integer;

   procedure WaitForEvent;

end ProofOfLife;