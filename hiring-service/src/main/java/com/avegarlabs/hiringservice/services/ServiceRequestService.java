package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ClientListItem;
import com.avegarlabs.hiringservice.dto.ServiceRequestListItem;
import com.avegarlabs.hiringservice.dto.ServiceRequestModel;
import com.avegarlabs.hiringservice.dto.ServiceTypeListItem;
import com.avegarlabs.hiringservice.models.Clients;
import com.avegarlabs.hiringservice.models.ServiceRequest;
import com.avegarlabs.hiringservice.models.ServiceType;
import com.avegarlabs.hiringservice.repositories.ContractsRespository;
import com.avegarlabs.hiringservice.repositories.ProjectRepository;
import com.avegarlabs.hiringservice.repositories.ServiceRequestRespository;
import com.avegarlabs.hiringservice.utils.CreateMoniker;
import com.avegarlabs.hiringservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceRequestService {

    private final ClientsService clientsService;
    private final ServiceRequestRespository respository;
    private final ServiceTypeService serviceTypeService;
    private final DateData dateData;
    private final CreateMoniker createMoniker;

    private final ContractsRespository contractsRespository;
    private final ProjectRepository projectRepository;

    public List<ServiceRequestListItem> findAll() {
        return respository.findAll().stream().map(this::mapListItemToServiceRequest).collect(Collectors.toList());
    }


    public ServiceRequest getRequestById(String id){
        return respository.findById(id).get();
    }

    public ServiceRequestListItem getRequestByMoniker(String moniker){
        ServiceRequest serviceRequest = respository.findByMoniker(moniker);
        return mapListItemToServiceRequest(serviceRequest);
    }

    public ServiceRequestListItem addService(ServiceRequestModel model) {
        ServiceRequest entity = mapServiceRequestFromModel(model);
        respository.save(entity);
        return mapListItemToServiceRequest(entity);
    }

    public ServiceRequestListItem updateService(String id, ServiceRequestModel model) {
        ServiceRequest serviceRequest = respository.findById(id).get();
        ServiceRequest updated = updateServiceRequestFromModel(serviceRequest, model);
        respository.save(updated);
        return mapListItemToServiceRequest(updated);
    }

    @Transactional
    public void deleteServiceRequest(String id) {
        ServiceRequest serviceRequest = respository.findById(id).get();
        try {
            contractsRespository.deleteByServiceRequest(serviceRequest);
            projectRepository.deleteByServiceRequest(serviceRequest);
        }catch (Exception e){
            log.error("Error in delete: ", e.getMessage());
        }

        respository.deleteById(id);
    }

    public ServiceRequestListItem mapListItemToServiceRequest(ServiceRequest model) {
        ClientListItem clientListItem = clientsService.mapClientListItemToClient(model.getClient());
        ServiceTypeListItem typeService = serviceTypeService.mapServiceTypeListItemToServiceType(model.getServiceType());
        return ServiceRequestListItem.builder()
                .id(model.getId())
                .name(model.getName())
                .accord(model.getAccord())
                .another(model.getAnother())
                .approveDate(model.getApproveDate())
                .techPrepDate(model.getTechPrepDate())
                .approveFor(model.getApproveFor())
                .byContract(model.getByContract())
                .cancelationDate(model.getCancelationDate())
                .engineeringService(model.getEngineeringService())
                .chargeContractSigning(model.getChargeContractSigning())
                .description(model.getDescription())
                .contractNumber(model.getContractNumber())
                .client(clientListItem)
                .serviceType(typeService)
                .constructionLicense(model.getConstructionLicense())
                .directionId(model.getDirectionId())
                .digitalInfo(model.getDigitalInfo())
                .includeOffer(model.getIncludeOffer())
                .ecologicalEstimation(model.getEcologicalEstimation())
                .environmentalLicense(model.getEnvironmentalLicense())
                .directionRepresentBy(model.getDirectionRepresentBy())
                .nameOfContractSigning(model.getNameOfContractSigning())
                .observations(model.getObservations())
                .microCertification(model.getMicroCertification())
                .planes(model.getPlanes())
                .state(model.getState())
                .othersDocs(model.getOthersDocs())
                .projectionTask(model.getProjectionTask())
                .technicalTask(model.getTechnicalTask())
                .queryOrganism(model.getQueryOrganism())
                .rejectDate(model.getRejectDate())
                .receptionDate(model.getReceptionDate())
                .resolution(model.getResolution())
                .lastUpdateTime(model.getLastUpdateTime())
                .representBy(model.getRepresentBy())
                .studyArea(model.getStudyArea())
                .workOrder(model.getWorkOrder())
                .resumeDate(model.getResumeDate())
                .moniker(model.getMoniker())
                .number(model.getNumber())
                .stopedDate(model.getStopedDate())
                .other(model.getOther())
                .creationDate(model.getCreationDate())
                .build();
    }

    public ServiceRequest mapServiceRequestFromModel(ServiceRequestModel model) {
        Clients clients = clientsService.getClientById(model.getClientId());
        ServiceType type = serviceTypeService.findServicebyId(model.getServiceTypeId());
        String moniker = createMoniker.createMoniker(model.getName());
         return ServiceRequest.builder()
                 .name(model.getName())
                 .accord(model.getAccord())
                 .another(model.getAnother())
                 .approveDate(dateData.checkDate(model.getApproveDate()))
                 .approveFor(model.getApproveFor())
                 .byContract(model.getByContract())
                 .cancelationDate(dateData.checkDate(model.getCancelationDate()))
                 .engineeringService(model.getEngineeringService())
                 .chargeContractSigning(model.getChargeContractSigning())
                 .description(model.getDescription())
                 .contractNumber(model.getContractNumber())
                 .client(clients)
                 .serviceType(type)
                 .constructionLicense(model.getConstructionLicense())
                 .directionId(model.getDirectionId())
                 .digitalInfo(model.getDigitalInfo())
                 .includeOffer(model.getIncludeOffer())
                 .ecologicalEstimation(model.getEcologicalEstimation())
                 .environmentalLicense(model.getEnvironmentalLicense())
                 .directionRepresentBy(model.getDirectionRepresentBy())
                 .nameOfContractSigning(model.getNameOfContractSigning())
                 .observations(model.getObservations())
                 .microCertification(model.getMicroCertification())
                 .planes(model.getPlanes())
                 .state(model.getState())
                 .othersDocs(model.getOthersDocs())
                 .projectionTask(model.getProjectionTask())
                 .technicalTask(model.getTechnicalTask())
                 .queryOrganism(model.getQueryOrganism())
                 .rejectDate(dateData.checkDate(model.getRejectDate()))
                 .techPrepDate(dateData.checkDate(model.getTechPrepDate()))
                 .receptionDate(dateData.checkDate(model.getReceptionDate()))
                 .resolution(model.getResolution())
                 .lastUpdateTime(dateData.today())
                 .creationDate(dateData.currentMonth())
                 .representBy(model.getRepresentBy())
                 .studyArea(model.getStudyArea())
                 .workOrder(model.getWorkOrder())
                 .resumeDate(dateData.checkDate(model.getResumeDate()))
                 .moniker(moniker)
                 .number(model.getNumber())
                 .stopedDate(dateData.checkDate(model.getStopedDate()))
                 .other(model.getOther())
                 .build();
    }

    public ServiceRequest updateServiceRequestFromModel(ServiceRequest entity, ServiceRequestModel model) {
        Clients clients = clientsService.getClientById(model.getClientId());
        ServiceType type = serviceTypeService.findServicebyId(model.getServiceTypeId());
        String moniker = createMoniker.createMoniker(model.getName());
        return ServiceRequest.builder()
                .id(entity.getId())
                .name(model.getName())
                .accord(model.getAccord())
                .another(model.getAnother())
                .approveDate(dateData.checkDate(model.getApproveDate()))
                .techPrepDate(dateData.checkDate(model.getTechPrepDate()))
                .approveFor(model.getApproveFor())
                .byContract(model.getByContract())
                .cancelationDate(dateData.checkDate(model.getCancelationDate()))
                .engineeringService(model.getEngineeringService())
                .chargeContractSigning(model.getChargeContractSigning())
                .description(model.getDescription())
                .contractNumber(model.getContractNumber())
                .client(clients)
                .serviceType(type)
                .constructionLicense(model.getConstructionLicense())
                .directionId(model.getDirectionId())
                .digitalInfo(model.getDigitalInfo())
                .includeOffer(model.getIncludeOffer())
                .ecologicalEstimation(model.getEcologicalEstimation())
                .environmentalLicense(model.getEnvironmentalLicense())
                .directionRepresentBy(model.getDirectionRepresentBy())
                .nameOfContractSigning(model.getNameOfContractSigning())
                .observations(model.getObservations())
                .microCertification(model.getMicroCertification())
                .planes(model.getPlanes())
                .state(model.getState())
                .othersDocs(model.getOthersDocs())
                .projectionTask(model.getProjectionTask())
                .technicalTask(model.getTechnicalTask())
                .queryOrganism(model.getQueryOrganism())
                .rejectDate(dateData.checkDate(model.getRejectDate()))
                .receptionDate(dateData.checkDate(model.getReceptionDate()))
                .resolution(model.getResolution())
                .lastUpdateTime(dateData.today())
                .representBy(model.getRepresentBy())
                .studyArea(model.getStudyArea())
                .workOrder(model.getWorkOrder())
                .resumeDate(dateData.checkDate(model.getResumeDate()))
                .moniker(moniker)
                .number(model.getNumber())
                .stopedDate(dateData.checkDate(model.getStopedDate()))
                .other(model.getOther())
                .creationDate(entity.getCreationDate())
                .build();
        }


}
