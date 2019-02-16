with Ada.Text_IO; use Ada.Text_IO;
with ProofOfLifeVBB;

package body ProofOfLife is
 
   function IsNoKeyAttack(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer) return Boolean is 
    
   begin

      if LastSampleIndex = 0 then
         -- Maybe it wrapped
         if Samples(1) = 0 then
            -- Maybe it happened to overflow to zero
            if Samples(2) = 0 then
               return True;
            end if;
         end if;
      end if;

      return False;

   end IsNoKeyAttack;

   function IsKeyScanningAttack(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer) return Boolean is 
       
   begin
      -- TODO

      return False;

   end IsKeyScanningAttack;

   function UniqueKeyPresses(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer) return Integer is 
       
   begin
      -- TODO

      return 5;

   end UniqueKeyPresses;

 
   procedure ProofOfLife(Samples : in ProofOfLifeVBB.KeySampleData ; LastSampleIndex : in Integer)  is
    --  N : Integer;
   begin
         if IsNoKeyAttack(Samples, LastSampleIndex) then
            Put_Line ("--Alert IsNoKeyAttack--");
         elsif IsKeyScanningAttack(Samples, LastSampleIndex) then
            Put_Line ("--Alert IsNoKeyAttack--");
         elsif UniqueKeyPresses(Samples,LastSampleIndex) = 5 then
            Put_Line ("--Unlock Authenticated--");
         else
            Put_Line ("--Uknown Attack--");
         end if;
 
   end ProofOfLife;
 
   procedure WaitForEvent is

      Samples : ProofOfLifeVBB.KeySampleData ;
      N : Integer;

   begin

         Put_Line ("--Sampling Keys while waiting for Unlock event--");
      
         N := ProofOfLifeVBB.CaptureKeyEvents(Samples);

         ProofOfLife(Samples, N);
 
 
   end WaitForEvent;

end ProofOfLife;

 