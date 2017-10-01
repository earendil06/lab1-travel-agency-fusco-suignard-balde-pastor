package travel.service;

import travel.data.TravelConfirmAnswer;
import travel.data.TravelConfirmRequest;

import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travel.service.ITravelApprovalService")
public class TravelApproval implements ITravelApprovalService {


    @Override
    public TravelConfirmAnswer travelConfirm(TravelConfirmRequest request) {
        TravelConfirmAnswer answer = new TravelConfirmAnswer();
        answer.setUuid(request.getUuid());
        try {
            //TODO send the mail to the owner demand
            answer.setSuccess(true);
        }catch (Exception e) {
            answer.setSuccess(false);
        }

        return answer;
    }
}
