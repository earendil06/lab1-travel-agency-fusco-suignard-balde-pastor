package fr.unice.groupe4.flows.utils;

import fr.unice.groupe4.flows.data.Expense;
import fr.unice.groupe4.flows.data.SupportingTravel;

public class RefundReport {

    public String write(SupportingTravel supporting) {

        StringBuilder b = new StringBuilder();

        b.append("Dear Employer , \n");
        b.append("\n");
        b.append("Vous allez etre remboursez pour le voyage " + supporting.getId() + "\n");
        b.append("\n");
        b.append("Que vous avez effectuez à :" + supporting.getCity() + "\n");
        b.append("\n");
        b.append("Pour un montant Total de :" + supporting.getTotalPrice() + "\n");
        b.append("\n");
        b.append("Ci dessous la liste des dépenses rempourser \n");
        b.append("\n");
        b.append(supporting.getExpenses().toString() + "\n");
        b.append("\n");

        return b.toString();

    }
}
